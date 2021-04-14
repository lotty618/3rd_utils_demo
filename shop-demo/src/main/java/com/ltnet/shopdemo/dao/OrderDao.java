package com.ltnet.shopdemo.dao;

import com.ltnet.shopdemo.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getOrderList(Integer pid);
    Order getOrderById(Integer id);
    int addOrder(Order order);
    int updateOrder(Order order);
}
