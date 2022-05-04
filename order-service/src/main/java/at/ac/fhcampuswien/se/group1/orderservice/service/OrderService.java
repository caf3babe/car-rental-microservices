package at.ac.fhcampuswien.se.group1.orderservice.service;

import at.ac.fhcampuswien.se.group1.orderservice.domain.dto.OrderRequest;
import at.ac.fhcampuswien.se.group1.orderservice.domain.exception.OrderNotFoundException;
import at.ac.fhcampuswien.se.group1.orderservice.domain.mapper.OrderMapper;
import at.ac.fhcampuswien.se.group1.orderservice.event.*;
import at.ac.fhcampuswien.se.group1.orderservice.model.*;
import at.ac.fhcampuswien.se.group1.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2 @RequiredArgsConstructor @Service public class OrderService {

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher publisher;
    
    private final OrderMapper orderMapper;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {

        Order order = orderMapper.create(orderRequest);

        order.setStatus(SagaStatus.CREATED);

        Car car = new Car();
        car.setCarId(BigInteger.valueOf(orderRequest.getCarId()));
        order.setCar(car);

        Location locationRental = new Location();
        locationRental.setLocationId(orderRequest.getLocationOfRentalId());
        order.setLocationOfRental(locationRental);

        Location locationReturn = new Location();
        locationReturn.setLocationId(orderRequest.getLocationOfReturnId());
        order.setLocationOfReturn(locationReturn);

        log.info("Saving an order {}", order);

        Order returnOrder = orderRepository.save(order);

        publishCreate(returnOrder);

        return returnOrder;
    }
    
    public void deleteOrderById(BigInteger id) {
        orderRepository.deleteById(id);
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }
    
    public Order getOrderById(BigInteger id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id" + id + " could not be found, ok bye."));
    }

    @Transactional
    public Order updateOrderById(BigInteger id, OrderRequest orderRequest) {
        Order oldOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order was not found for " + id));
        Order updatedOrder = orderMapper.update(orderRequest, oldOrder);

        updatedOrder.setStatus(SagaStatus.CREATED);

        log.info("Updating an order {}", updatedOrder);

        Order returnOrder = orderRepository.save(updatedOrder);

        publishUpdate(returnOrder);

        return returnOrder;
    }
    
    public Order updateStatusById(BigInteger id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order was not found for " + id));
        OrderStatus oldOrderStatus = order.getOrderStatus();
        order.setOrderStatus(orderStatus);
        order.setStatus(SagaStatus.CREATED);

        log.info("Updating an order {}", order);

        Order updateOrder = orderRepository.save(order);

        publishUpdateStatus(updateOrder, oldOrderStatus);

        return updateOrder;
    }

    private void publishCreate(Order order) {

        OrderCreateEvent event = new OrderCreateEvent(UUID.randomUUID().toString(), order);

        log.info("Publishing an Order created event {}", event);

        publisher.publishEvent(event);

    }

    private void publishUpdate(Order order) {

        OrderUpdateEvent event = new OrderUpdateEvent(UUID.randomUUID().toString(), order);

        log.info("Publishing an Order updated event {}", event);

        publisher.publishEvent(event);

    }

    private void publishUpdateStatus(Order order, OrderStatus orderStatus) {

        OrderUpdateStatusEvent event = new OrderUpdateStatusEvent(UUID.randomUUID().toString(), order, orderStatus);

        log.info("Publishing an Order updated status event {}", event);

        publisher.publishEvent(event);

    }
    
    @Transactional
    public void updateOrderCarUnavailable(Order order) {

        log.info("Canceling Order because Car isn't available {}", order);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        if (optionalOrder.isPresent()) {

            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(SagaStatus.CAR_REJECTED);
            orderRepository.save(updateOrder);

            log.info("Order {} was canceled - CAR_REJECTED", updateOrder.getOrderId());

        } else {

            log.info("Cannot find an order {}", order.getOrderId());

        }

    }

    
    @Transactional
    public void updateOrderLocationExistent(Order order) {
        log.info("Updating Order {} to {}", order, SagaStatus.FINISHED);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        if (optionalOrder.isPresent()) {

            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(SagaStatus.FINISHED);
            updateOrder.setCar(order.getCar());
            updateOrder.setLocationOfRental(order.getLocationOfRental());
            updateOrder.setLocationOfReturn(order.getLocationOfReturn());
            orderRepository.save(updateOrder);

            log.info("Order updated Status {} done", updateOrder.getOrderId());

        } else {

            log.error("Cannot update Order to status {}, Order {} not found", SagaStatus.FINISHED, order);

        }

    }

    @Transactional
    public void updateOrderLocationNonexistent(Order order) {

        log.info("Canceling Order because Location isn't existent {}", order);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        if (optionalOrder.isPresent()) {

            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(SagaStatus.LOCATION_REJECTED);
            orderRepository.save(updateOrder);

            log.info("Order {} was canceled - LOCATION_REJECTED", updateOrder.getOrderId());

        } else {

            log.info("Cannot find an order {}", order.getOrderId());

        }
    }

    public void updateOrderStatusSuccess(Order order) {

        log.info("Updating Order {} to {}", order, SagaStatus.FINISHED);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        if (optionalOrder.isPresent()) {

            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(SagaStatus.FINISHED);
            updateOrder.setCar(order.getCar());

            orderRepository.save(updateOrder);

            log.info("Order updated status success {} done", updateOrder.getOrderId());

        } else {

            log.error("Cannot update Order to status {}, Order {} not found - status success", SagaStatus.FINISHED, order);

        }
    }

    public void updateOrderStatusFailure(Order order, OrderStatus orderOldStatus) {
        log.info("Revert Order status{} to {}", order, SagaStatus.FINISHED);

        Optional<Order> optionalOrder = orderRepository.findById(order.getOrderId());

        if (optionalOrder.isPresent()) {

            Order updateOrder = optionalOrder.get();
            updateOrder.setStatus(SagaStatus.FINISHED);
            updateOrder.setOrderStatus(orderOldStatus);
            orderRepository.save(updateOrder);

            log.info("Order updated status failure {} done", updateOrder.getOrderId());

        } else {

            log.error("Cannot update Order to status {}, Order {} not found - status failure", SagaStatus.FINISHED, order);

        }
    }
}
