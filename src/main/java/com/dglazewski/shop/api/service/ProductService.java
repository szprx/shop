package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;

import java.util.List;

public interface ProductService {
    DataBaseStatusResponse<Product> addProduct(Product product);

    DataBaseStatusResponse<Product> updateProduct(Long id, Product product);

    DataBaseStatusResponse<Product> getProduct(Long id);

    DataBaseStatusResponse<List<Product>> getAllProducts();
}
