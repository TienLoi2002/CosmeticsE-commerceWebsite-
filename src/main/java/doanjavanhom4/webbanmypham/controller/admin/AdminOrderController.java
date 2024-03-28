package doanjavanhom4.webbanmypham.controller.admin;

import doanjavanhom4.webbanmypham.entities.Order;
import doanjavanhom4.webbanmypham.repositories.OrderDetailRepository;
import doanjavanhom4.webbanmypham.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        if(keyword != null && !keyword.trim().isEmpty())
        {
//            model.addAttribute("products", orderRepository.searchOrder(keyword.trim()));
        }else
        {
            model.addAttribute("products", orderRepository.findAll());
        }
        return "admin/order/index";
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


        return "redirect:/admin/order";
    }

    @GetMapping("/{id}")
    public String create(@PathVariable("id") Integer id, Model model) {
        System.out.println(orderDetailRepository.findByOrders(orderRepository.findById(id).orElse(null)));
        model.addAttribute("model", orderDetailRepository.findByOrders(orderRepository.findById(id).orElse(null)));

        return "admin/order/detail";
    }
}
