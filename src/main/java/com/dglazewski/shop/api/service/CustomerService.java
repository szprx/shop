package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;

public interface CustomerService extends CardService {
    DataBaseStatusResponse<Customer> saveCustomer(Customer newCustomer);

    DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer updatedCustomer);//edycja konta gdzie cascadeType powinno automatycznie zaktualizowac reszte

    DataBaseStatusResponse<Customer> getCustomer(Long id);//wyswietlanie danych i dane do zamowien

    DataBaseStatusResponse<Customer> deleteCustomer(Long id);//dezaktywacja konta, dane ciagle beda przechowywane - przez jakis czas - mozliwe dodanie dnia rejestracji

    DataBaseStatusResponse<Customer> getCustomer(String email);//wymagana do weryfikacji konta
}