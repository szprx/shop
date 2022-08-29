package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface CustomerService {
    DataBaseStatusResponse<Customer> createCustomer(Customer newCustomer);//zakladanie konta aczkolwiek przy dodawaniu usera juz to bedzie obslugiwane

    DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer updatedCustomer);//edycja konta gdzie cascade powinno automatycznie zaktualizowac reszte

    DataBaseStatusResponse<Customer> getCustomer(Long id);//wyswietlanie danych i dane do zamowien

    DataBaseStatusResponse<Customer> deleteCustomer(Long id);//usuwanie konta bedzie sie dzialo przez endpoint usera

    DataBaseStatusResponse<Customer> getCustomer(String email);//usuwanie konta bedzie sie dzialo przez endpoint usera

    DataBaseStatusResponse<Customer> register(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    DataBaseStatusResponse<User> verify(String verificationCode);

}
