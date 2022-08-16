package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;

public interface CustomerService {
    DataBaseStatusResponse<Customer> addCustomer(Customer newCustomer);//zakladanie konta aczkolwiek przy dodawaniu usera juz to bedzie obslugiwane

    DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer updatedCustomer);//edycja konta gdzie cascade powinno automatycznie zaktualizowac reszte

    DataBaseStatusResponse<Customer> getCustomer(Long id);//wyswietlanie danych i dane do zamowien

    DataBaseStatusResponse<Customer> deleteCustomer(Long id);//usuwanie konta bedzie sie dzialo przez endpoint usera

}
