package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.entity.Order;

public interface OrderService {
    Order addOrder(Order newOrder);//dodanie nowego zamowienia

    Order updateOrder(Long id, Order updatedOrder);//zmiana stanu zamowienia

    Order getOrder(Long id);//pobranie zamowienia

}
