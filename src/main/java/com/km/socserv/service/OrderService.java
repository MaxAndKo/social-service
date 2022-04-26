package com.km.socserv.service;

import com.km.socserv.entity.Order;
import com.km.socserv.entity.User;
import com.km.socserv.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order findById(int id){
        Order order = null;
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent())
            order = optional.get();
        return order;
    }

    public boolean setUser(Order order){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            order.setUser(user);
            return true;
        }
        return false;
    }

    public void save(Order order){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy в HH:mm");

        if (order.getUser() == null)
            setUser(order);

        if (order.getStatus() == Order.StatusEnum.ОТПРАВЛЕНО)
            order.setStartDate(dateFormat.format(new Date()));

        if (order.getStatus() == Order.StatusEnum.ВЫПОЛНЕНО || order.getStatus() == Order.StatusEnum.ОТМЕНЕНО)
            order.setFinalDate(dateFormat.format(new Date()));

        orderRepository.save(order);
    }

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }

    public List<Order> findAllActiveOrders(){
        return orderRepository.findAllByStatusNotAndStatusNot(Order.StatusEnum.ОТМЕНЕНО, Order.StatusEnum.ВЫПОЛНЕНО);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public List<Order> findAllInactiveOrders(){
        return orderRepository.findAllByStatusOrStatus(Order.StatusEnum.ОТМЕНЕНО, Order.StatusEnum.ВЫПОЛНЕНО);
    }

    public boolean updateOrderStatusAndExecutor(Order newOrder){
        if (newOrder != null && newOrder.getId() != 0){
            Optional<Order> optional = orderRepository.findById(newOrder.getId());
            if (optional.isPresent()){
                Order dBOrder = optional.get();
                if (newOrder.getStatus() != null)
                    dBOrder.setStatus(newOrder.getStatus());
                if (dBOrder.getExecutor() == null && newOrder.getExecutor() != null)
                    dBOrder.setExecutor(newOrder.getExecutor());
                save(dBOrder);
                return true;
            }
        }
        return false;
    }
}
