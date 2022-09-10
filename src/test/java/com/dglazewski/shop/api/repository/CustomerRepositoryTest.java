package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.Card;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        long id = 1L;
        String name = "testName";
        String lastName = "testLastName";

        String email = "testEmail@gmail.com";
        String password = "testPassword";
        String verificationCode = "testVerificationCode";
        boolean enabled = true;
        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;
        User user = new User(id, email, password, verificationCode, enabled, roleCustomer);

        List<Order> orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();

        Card card = new Card(id, products);

        testCustomer = new Customer(id, name, lastName, user, orders, card);
        customerRepository.save(testCustomer);
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void itShouldFindCustomerByEmail() {
        //given
        //when
        Customer retrievedCustomer = customerRepository.findByUserEmail(testCustomer.getUser().getEmail()).get();
        //then
        assertThat(retrievedCustomer.getName()).isEqualTo(testCustomer.getName());
        assertThat(retrievedCustomer.getLastName()).isEqualTo(testCustomer.getLastName());

        assertThat(retrievedCustomer.getUser().getEmail()).isEqualTo(testCustomer.getUser().getEmail());
        assertThat(retrievedCustomer.getUser().getPassword()).isEqualTo(testCustomer.getUser().getPassword());
        assertThat(retrievedCustomer.getUser().getVerificationCode()).isEqualTo(testCustomer.getUser().getVerificationCode());
        assertThat(retrievedCustomer.getUser().isEnabled()).isEqualTo(testCustomer.getUser().isEnabled());
        assertThat(retrievedCustomer.getUser().getRole()).isEqualTo(testCustomer.getUser().getRole());
    }

    @Test
    void itShouldNotFindCustomerByEmail_BecauseRecordDoesntExist() {
        //given
        //when
        Optional<Customer> optionalCustomer = customerRepository.findByUserEmail("wrongTestEmail@gmail.com");
        //then
        assertThat(optionalCustomer).isEmpty();
    }
}