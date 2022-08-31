package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;

public interface CustomerService {
    DataBaseStatusResponse<Customer> saveCustomer(Customer newCustomer);

    DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer updatedCustomer);//edycja konta gdzie cascade powinno automatycznie zaktualizowac reszte

    DataBaseStatusResponse<Customer> getCustomer(Long id);//wyswietlanie danych i dane do zamowien

    DataBaseStatusResponse<Customer> deleteCustomer(Long id);//usuwanie konta bedzie sie dzialo przez endpoint usera

    DataBaseStatusResponse<Customer> getCustomer(String email);//usuwanie konta bedzie sie dzialo przez endpoint usera
}