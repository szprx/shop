package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.CustomerRepository;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public DataBaseStatusResponse<Customer> addCustomer(Customer newCustomer) {
        Optional<User> user = userRepository.findByEmail(newCustomer.getUser().getEmail());
        if (user.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        if (!newCustomer.isValid()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_HAS_NOT_VALID_FIELDS);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customerRepository.save(Customer.create(
                        newCustomer.getName(),
                        newCustomer.getLastName(),
                        newCustomer.getUser().getEmail(),
                        passwordEncoder.encode(newCustomer.getUser().getPassword()))));
    }

    @Override
    public DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer newCustomer) {
        //TODO add checking if used email is occupied
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
