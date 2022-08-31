package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Order;

public interface OrderService {
    DataBaseStatusResponse<Order> addOrder(Order newOrder);//dodanie nowego zamowienia

    DataBaseStatusResponse<Order> updateOrder(Long id, Order updatedOrder);//zmiana stanu zamowienia

    DataBaseStatusResponse<Order> getOrder(Long id);//pobranie zamowienia
}
