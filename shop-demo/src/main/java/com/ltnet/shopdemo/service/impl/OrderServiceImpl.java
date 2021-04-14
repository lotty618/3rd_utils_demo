package com.ltnet.shopdemo.service.impl;

import com.ltnet.shopdemo.dao.OrderDao;
import com.ltnet.shopdemo.entity.Order;
import com.ltnet.shopdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> getOrderList(String pid) {
        return orderDao.getOrderList(Integer.parseInt(pid));
    }

    @Override
    public Order getOrderById(String id) {
        return orderDao.getOrderById(Integer.parseInt(id));
    }

    @Override
    public boolean addOrder(Order order) {
        try {
            return orderDao.addOrder(order) > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return orderDao.updateOrder(order) > 0 ? true : false;
    }
}
