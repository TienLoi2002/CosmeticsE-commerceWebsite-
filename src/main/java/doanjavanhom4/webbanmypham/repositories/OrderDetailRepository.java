package doanjavanhom4.webbanmypham.repositories;

import doanjavanhom4.webbanmypham.entities.Order;
import doanjavanhom4.webbanmypham.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer>, CrudRepository<OrderDetails, Integer> {


    List<OrderDetails> findByOrders(Order orders);
}
