package doanjavanhom4.webbanmypham.controller;

import doanjavanhom4.webbanmypham.entities.Order;
import doanjavanhom4.webbanmypham.repositories.OrderDetailRepository;
import doanjavanhom4.webbanmypham.repositories.OrderRepository;
import doanjavanhom4.webbanmypham.service.CustomerService;
import doanjavanhom4.webbanmypham.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @GetMapping("")
    public String index(Model model, Principal principal) {
        if(principal==null){
            return "redirect:/login";
        }else {
//            model.addAttribute("product", orderRepository.findAll());
            String customerName = principal.getName();

            List<Order> orderList = orderService.getOrderHistoryForUser(customerName);
            // Lấy lịch sử đặt hàng của người dùng từ OrderService
            model.addAttribute("orders",orderList );
            return "Order/index";
        }

    }

    @GetMapping("/UpdateStatus")
    public String update(Model model, @RequestParam("type") Integer type, @RequestParam("id") Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (type == 1) {
            assert order != null;
            order.setStatus(1);
            orderRepository.save(order);
        }
        if (type == 2) {
            assert order != null;
            order.setStatus(2);
            orderRepository.save(order);
        }
        return "redirect:/order";
    }

    @GetMapping("/{id}")
    public String create(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("model", orderDetailRepository.findByOrders(orderRepository.findById(id).orElse(null)));
        return "/Order/detail";
    }
}
