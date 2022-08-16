package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.CustomerRepository;
import com.dglazewski.shop.api.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;


    @Override
    public DataBaseStatusResponse<Customer> addCustomer(Customer newCustomer) {
        Optional<Customer> customer = customerRepository.findByName(newCustomer.getName());
        if (customer.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        if (!newCustomer.isValid()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_HAS_NOT_VALID_FIELDS);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customerRepository.save(newCustomer));
    }

    @Override
    public DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer newCustomer) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        if (!newCustomer.isValid()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_HAS_NOT_VALID_FIELDS
            );
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
                        Status.RECORD_ALREADY_EXIST));
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
    public DataBaseStatusResponse<Customer> deleteCustomer(Long id) {
        return new DataBaseStatusResponse<>(
                Status.RECORD_DELETED_SUCCESSFULLY);
    }
}
