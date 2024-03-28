package doanjavanhom4.webbanmypham.service;

import doanjavanhom4.webbanmypham.entities.Customer;
import doanjavanhom4.webbanmypham.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository userRepository;

    public void save(Customer user){
        userRepository.save(user);
    }
    public Customer findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public Customer findByEmail(String name) {
        return userRepository.findByEmail(name);
    }

    public Customer findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
