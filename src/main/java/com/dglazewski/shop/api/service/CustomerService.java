package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.dto.CustomerDto;
import com.dglazewski.shop.api.dto.creator.CustomerCreatorDto;

public interface CustomerService {
    CustomerDto addCustomer(CustomerCreatorDto newCustomer);//zakladanie konta aczkolwiek przy dodawaniu usera juz to bedzie obslugiwane

    CustomerDto updateCustomer(Long id, CustomerCreatorDto updatedCustomer);//edycja konta gdzie cascade powinno automatycznie zaktualizowac reszte

    CustomerDto getCustomer(Long id);//wyswietlanie danych i dane do zamowien

    void deleteCustomer(Long id);//usuwanie konta bedzie sie dzialo przez endpoint usera

}
