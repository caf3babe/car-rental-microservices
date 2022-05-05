package at.ac.fhcampuswien.se.group1.authenticationservice.service;

import at.ac.fhcampuswien.se.group1.authenticationservice.domain.exception.OrderNotFoundException;
import at.ac.fhcampuswien.se.group1.authenticationservice.model.Order;
import at.ac.fhcampuswien.se.group1.authenticationservice.repository.OrderRepository;
import at.ac.fhcampuswien.se.group1.authenticationservice.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OrderRepository orderRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    public String loginAdmin(String username, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtUtil.generateToken(username);
    }
    
    public String loginOrder(BigInteger orderId, String lastName) throws OrderNotFoundException {
        logger.info("Logging user in using orderId and lastName");
        Order order = orderRepository.findByOrderIdAndLastNameAllIgnoreCase(orderId, lastName).orElseThrow(
                () -> {
                    throw new OrderNotFoundException(String.format("Order with id %s was not found", orderId.toString()));
                }
        );
        return jwtUtil.generateToken(order.getLastName());
    }
}
