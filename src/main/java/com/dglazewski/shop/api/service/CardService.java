package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Product;

public interface CardService {
    DataBaseStatusResponse<Customer> addProductToCustomerCard(String email,Product product);

    DataBaseStatusResponse<Customer> deleteProductFromCustomerCard(String email,Product product);
}
