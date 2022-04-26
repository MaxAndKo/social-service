package com.km.socserv.repository;

import com.km.socserv.entity.Order;
import com.km.socserv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAllByUser(User user);
    public List<Order> findAllByStatus(Order.StatusEnum statusEnum1);
    public List<Order> findAllByStatusNotAndStatusNot(Order.StatusEnum statusEnum1, Order.StatusEnum statusEnum2);
    public List<Order> findAllByStatusOrStatus(Order.StatusEnum statusEnum1, Order.StatusEnum statusEnum2);

}
