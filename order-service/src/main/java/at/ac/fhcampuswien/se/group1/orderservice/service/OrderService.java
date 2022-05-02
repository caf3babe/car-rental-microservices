package at.ac.fhcampuswien.se.group1.orderservice.service;


import at.ac.fhcampuswien.se.group1.orderservice.domain.dto.OrderRequest;
import at.ac.fhcampuswien.se.group1.orderservice.domain.exception.OrderNotFoundException;
import at.ac.fhcampuswien.se.group1.orderservice.domain.mapper.OrderMapper;
import at.ac.fhcampuswien.se.group1.orderservice.event.*;
import at.ac.fhcampuswien.se.group1.orderservice.model.Order;
import at.ac.fhcampuswien.se.group1.orderservice.model.OrderStatus;
import at.ac.fhcampuswien.se.group1.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Log4j2 @RequiredArgsConstructor @Service public class OrderService {
    
    private final OrderRepository orderRepository;
    
    // TODO replace with messaging
    // private final CarRepository carRepository;
    
    // TODO replace with messaging
    // private final LocationRepository locationRepository;
    
    private final ApplicationEventPublisher publisher;
    
    private final OrderMapper orderMapper;
    
    public Order createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.create(orderRequest);
        return createOrUpdateOrder(orderRequest, order);
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
    
    public Order updateOrderById(BigInteger id, OrderRequest orderRequest) {
        Order oldOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order was not found for " + id));
        Order newOrder = orderMapper.update(orderRequest, oldOrder);
        return createOrUpdateOrder(orderRequest, newOrder);
    }
    
    public Order updateStatusById(BigInteger id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order was not found for " + id));
        order.setOrderStatus(orderStatus);
        // TODO replace with messaging
        /*
        Car car = carRepository.findById(order.getCar().getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car was not found for " + order.getCar().getCarId()));
        if (orderStatus == OrderStatus.ACTIVE) {
            car.setCarStatus(CarStatus.RENTED);
        } else {
            car.setCarStatus(CarStatus.AVAILABLE);
        }*/
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order createOrUpdateOrder(OrderRequest orderRequest, Order newOrder) {
        // TODO replace with messaging
        /*
        Car car = carRepository.findById(orderRequest.getCarId())
                .orElseThrow(() -> new CarNotFoundException("Car was not found for " + orderRequest.getCarId()));
        Location locationRental = locationRepository.findById(orderRequest.getLocationOfRentalId()).orElseThrow(
                () -> new LocationNotFoundException(
                        "Location of rental was not found for " + orderRequest.getLocationOfRentalId()));
        Location locationReturn = locationRepository.findById(orderRequest.getLocationOfReturnId()).orElseThrow(
                () -> new LocationNotFoundException(
                        "Location of return was not found for " + orderRequest.getLocationOfReturnId()));
        newOrder.setCar(car);
        newOrder.setLocationOfRental(locationRental);
        newOrder.setLocationOfReturn(locationReturn);
        */
        
        log.info("Got request to create order: {}", orderRequest);
        log.info("with mapped order object: {}", newOrder);
        
        
        Order order = orderRepository.save(newOrder);
        
        OrderInitEvent event = new OrderInitEvent(order.getOrderId(), BigInteger.valueOf(orderRequest.getCarId()),
                orderRequest.getLocationOfRentalId(), orderRequest.getLocationOfReturnId());
        log.info("Sending order init event {}", event);
        
        publisher.publishEvent(event);
        
        
        // remove values, so we can check it when corresponding events are received
        newOrder.setCar(null);
        newOrder.setLocationOfReturn(null);
        // TODO: create dedicated event for RentalLocation
        // newOrder.setLocationOfRental(null);
        
        return order;
    }
    
    @Transactional
    public void updateOrderCarUnavailable(CarUnavailableEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> order.setOrderStatus(OrderStatus.CANCELED));
    }
    
    @Transactional
    public void updateOrderCarAvailable(CarAvailableEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            order.setCar(event.getCar());
            if (order.getLocationOfRental() != null && order.getLocationOfReturn() != null) {
                // TODO do something do indicate order is confirmed, another OrderStatus: CONFIRMED?
                log.info("Confirming order {}", order.getOrderId());
            }
            
            orderRepository.save(order);
            
        }, () -> {
            log.error("Order with id {} was not found", event.getOrderId());
        });
    }
    
    @Transactional
    public void updateOrderLocationNonexistent(LocationNonexistentEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> order.setOrderStatus(OrderStatus.CANCELED));
    }
    
    @Transactional
    public void updateOrderLocationExistent(LocationExistentEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresentOrElse(order -> {
            
            order.setLocationOfRental(event.getLocation());
            if (order.getCar() != null && order.getLocationOfReturn() != null) {
                // TODO do something do indicate order is confirmed, another OrderStatus: CONFIRMED?
                log.info("Confirming order {}", order.getOrderId());
            }
        }, () -> {
            log.error("Order with id {} was not found", event.getOrderId());
        });
    }
    
}
