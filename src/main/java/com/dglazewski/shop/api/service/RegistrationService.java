package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface RegistrationService {
    DataBaseStatusResponse<Customer> register(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException;

    DataBaseStatusResponse<User> verify(String verificationCode);
}