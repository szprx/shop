package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.Card;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void itShouldCheckIfCustomerIsSavedAndReturnEquals() {
        //given
        long id = 1L;
        String name = "testName";
        String lastName = "testLastName";

        String email = "textEmail@gmail.com";
        String password = "testPassword";
        String verificationCode = "verificationCodeTest";
        boolean enabled = true;
        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;

        User user = new User(id, email, password, verificationCode, enabled, roleCustomer);

        Card card = new Card(id, new ArrayList<>());
        Customer customer = new Customer(id, name, lastName, user, new ArrayList<>(), card);
        customerRepository.save(customer);
        //when
        Customer expected = customerRepository.findByUserEmail(email).get();
        //then
        assertThat(expected.getName()).isEqualTo(customer.getName());
        assertThat(expected.getLastName()).isEqualTo(customer.getLastName());
        assertThat(expected.getUser()).isEqualTo(customer.getUser());
    }
}