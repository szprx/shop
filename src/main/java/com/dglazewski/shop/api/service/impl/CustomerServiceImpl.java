package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.dto.CustomerDto;
import com.dglazewski.shop.api.dto.creator.CustomerCreatorDto;
import com.dglazewski.shop.api.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    @Override
    public CustomerDto addCustomer(CustomerCreatorDto newCustomer) {
        return null;
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerCreatorDto updatedCustomer) {
        return null;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }
}
