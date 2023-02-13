package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.service.OrderService;

public class OrderServiceImpl implements OrderService {
    @Override
    public DataBaseStatusResponse<Order> addOrder(Order newOrder) {
        return null;
    }

    @Override
    public DataBaseStatusResponse<Order> updateOrder(Long id, Order updatedOrder) {
        return null;
    }

    @Override
    public DataBaseStatusResponse<Order> getOrder(Long id) {
        return null;
    }
}
