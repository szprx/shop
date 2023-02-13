package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.CustomerRepository;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    @Override
    public DataBaseStatusResponse<Customer> saveCustomer(Customer newCustomer) {
        Optional<User> user = userRepository.findByEmail(newCustomer.getUser().getEmail());
        if (user.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customerRepository.save(newCustomer));
    }

    @Override
    public DataBaseStatusResponse<Customer> getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                customer.get());
    }

    @Override
    public DataBaseStatusResponse<Customer> getCustomer(String email) {
        Optional<Customer> customer = customerRepository.findByUserEmail(email);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                customer.get());
    }

    @Override
    public DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer newCustomer) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        Optional<User> user = userRepository.findByEmail(newCustomer.getUser().getEmail());
        if (user.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        return customer
                .map(oldCustomer -> {
                    Customer updatedCustomer = oldCustomer.updateWith(newCustomer);
                    customerRepository.save(updatedCustomer);
                    return new DataBaseStatusResponse<>(
                            Status.RECORD_UPDATED_SUCCESSFULLY,
                            updatedCustomer);
                })
                .orElse(new DataBaseStatusResponse<>(
                        Status.UNKNOWN_DATABASE_ERROR));
    }

    @Override
    public DataBaseStatusResponse<Customer> deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        customerRepository.deleteById(id);
        return new DataBaseStatusResponse<>(
                Status.RECORD_DELETED_SUCCESSFULLY);
    }


    @Override
    public DataBaseStatusResponse<Customer> addProductToCustomerCard(String email, Product product) {
        Optional<Customer> customer = customerRepository.findByUserEmail(email);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        DataBaseStatusResponse<Product> response = productService.changeAmount(product, -1);
        if (response.getStatus() != Status.RECORD_UPDATED_SUCCESSFULLY) {
            return new DataBaseStatusResponse<>(
                    response.getStatus()
            );
        }
        customer.get()
                .getShoppingCard()
                .getProducts()
                .add(response.getEntity());

        customerRepository.save(customer.get());
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customer.get()
        );
    }

    @Override
    public DataBaseStatusResponse<Customer> deleteProductFromCustomerCard(String email, Product product) {
        Optional<Customer> customer = customerRepository.findByUserEmail(email);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        DataBaseStatusResponse<Product> response = productService.changeAmount(product, 1);
        if (response.getStatus() != Status.RECORD_UPDATED_SUCCESSFULLY) {
            return new DataBaseStatusResponse<>(
                    response.getStatus()
            );
        }
        customer.get()
                .getShoppingCard()
                .getProducts()
                .remove(product);

        customerRepository.save(customer.get());
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customer.get()
        );
    }
}