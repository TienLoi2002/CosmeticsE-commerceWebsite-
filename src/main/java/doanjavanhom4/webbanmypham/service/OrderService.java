package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.Order;
import doanjavanhom4.webbanmypham.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrderHistoryForUser(String email) {
        return orderRepository.findByEmail(email);
    }

}
