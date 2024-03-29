package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.ProductRepository;
import com.dglazewski.shop.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public DataBaseStatusResponse<Product> saveProduct(Product newProduct) {
        Optional<Product> product = productRepository.findByName(newProduct.getName());
        if (product.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                productRepository.save(newProduct));
    }

    @Override
    public DataBaseStatusResponse<Product> getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                product.get());
    }

    @Override
    public DataBaseStatusResponse<List<Product>> getAllProducts() {
        return new DataBaseStatusResponse<>(Status.RECORD_RETRIEVED_SUCCESSFULLY, productRepository.findAll());

    }

    @Override
    public DataBaseStatusResponse<Product> changeAmount(Product product, int amountChange) {
        int newAmount = product.getAmount() + amountChange;
        if (newAmount<0) {
            return new DataBaseStatusResponse<>(
                    Status.OPERATION_FORBIDDEN);
        }
        product.setAmount(newAmount);
        return updateProduct(product.getId(), product);
    }

    @Override
    public DataBaseStatusResponse<Product> updateProduct(Long id, Product newProduct) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return product
                .map(oldProduct -> {
                    Product updatedProduct = oldProduct.updateWith(newProduct);
                    productRepository.save(updatedProduct);
                    return new DataBaseStatusResponse<>(
                            Status.RECORD_UPDATED_SUCCESSFULLY,
                            updatedProduct);
                })
                .orElse(new DataBaseStatusResponse<>(
                        Status.UNKNOWN_DATABASE_ERROR));
    }
}