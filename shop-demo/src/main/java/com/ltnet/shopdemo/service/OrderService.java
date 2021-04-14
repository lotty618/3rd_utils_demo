package com.ltnet.shopdemo.service;

import com.ltnet.shopdemo.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrderList(String pid);
    Order getOrderById(String id);
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
}
