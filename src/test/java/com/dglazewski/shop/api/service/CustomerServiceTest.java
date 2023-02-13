//package com.dglazewski.shop.api.service;
//
//import com.dglazewski.shop.api.entity.Card;
//import com.dglazewski.shop.api.entity.Customer;
//import com.dglazewski.shop.api.entity.Order;
//import com.dglazewski.shop.api.entity.Product;
//import com.dglazewski.shop.api.entity.User;
//import com.dglazewski.shop.api.enums.RoleEnum;
//import com.dglazewski.shop.api.enums.Status;
//import com.dglazewski.shop.api.repository.CustomerRepository;
//import com.dglazewski.shop.api.repository.UserRepository;
//import com.dglazewski.shop.api.service.impl.CustomerServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CustomerServiceTest {
//
//    private CustomerService customerServiceUnderTest;
//
//    @Mock
//    private CustomerRepository customerRepository;
//    @Mock
//    private UserRepository userRepository;
//
//    private Customer testCustomer;
//
//    @BeforeEach
//    void setUp() {
//        this.customerServiceUnderTest = new CustomerServiceImpl(customerRepository, userRepository);
//
//        Long id = 1L;
//        String name = "testName";
//        String lastName = "testLastName";
//
//        String email = "textEmail@gmail.com";
//        String password = "testPassword";
//        String verificationCode = "verificationCodeTest";
//        boolean enabled = true;
//        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;
//
//        User user = new User(id, email, password, verificationCode, enabled, roleCustomer);
//
//        List<Order> orders = new ArrayList<>();
//
//        List<Product> products = new ArrayList<>();
//
//        Card card = new Card(id, products);
//
//        testCustomer = new Customer(id, name, lastName, user, orders, card);
//    }
//
//    @Test
//    void itShouldSaveCustomer() {
//        when(userRepository.findByEmail(anyString()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.saveCustomer(
//                testCustomer).getStatus())
//                .isEqualTo(Status.RECORD_CREATED_SUCCESSFULLY);
//    }
//
//    @Test
//    void itShouldNotSaveCustomer_BecauseRecordWithThisEmailAlreadyExist() {
//        when(userRepository.findByEmail(anyString()))
//                .thenReturn(Optional.of(testCustomer.getUser()));
//
//        assertThat(customerServiceUnderTest.saveCustomer(
//                testCustomer).getStatus())
//                .isEqualTo(Status.RECORD_ALREADY_EXIST);
//    }
//
//    @Test
//    void itShouldNotGetCustomerById_BecauseRecordDoestExist() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.getCustomer(
//                testCustomer.getId()).getStatus())
//                .isEqualTo(Status.RECORD_DOESNT_EXIST);
//    }
//
//    @Test
//    void itShouldGetCustomerById() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.of(testCustomer));
//
//        assertThat(customerServiceUnderTest.getCustomer(
//                testCustomer.getId()).getStatus())
//                .isEqualTo(Status.RECORD_RETRIEVED_SUCCESSFULLY);
//    }
//
//    @Test
//    void itShouldNotGetCustomerByEmail_BecauseRecordDoestExist() {
//        when(customerRepository.findByUserEmail(anyString()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.getCustomer(
//                testCustomer.getUser().getEmail()).getStatus())
//                .isEqualTo(Status.RECORD_DOESNT_EXIST);
//    }
//
//    @Test
//    void itShouldGetCustomerByEmail() {
//        when(customerRepository.findByUserEmail(anyString()))
//                .thenReturn(Optional.of(testCustomer));
//
//        assertThat(customerServiceUnderTest.getCustomer(
//                testCustomer.getUser().getEmail()).getStatus())
//                .isEqualTo(Status.RECORD_RETRIEVED_SUCCESSFULLY);
//    }
//
//    @Test
//    void itShouldUpdateCustomer() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.of(testCustomer));
//        when(userRepository.findByEmail(anyString()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.updateCustomer(
//                testCustomer.getId(), testCustomer).getStatus())
//                .isEqualTo(Status.RECORD_UPDATED_SUCCESSFULLY);
//    }
//
//    @Test
//    void itShouldNotUpdateCustomer_BecauseRecordDoesntExist() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.updateCustomer(
//                testCustomer.getId(), testCustomer).getStatus())
//                .isEqualTo(Status.RECORD_DOESNT_EXIST);
//    }
//
//    @Test
//    void itShouldNotUpdateCustomer_BecauseRecordWithThisEmailAlreadyExist() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.of(testCustomer));
//        when(userRepository.findByEmail(anyString()))
//                .thenReturn(Optional.of(testCustomer.getUser()));
//
//        assertThat(customerServiceUnderTest.updateCustomer(
//                testCustomer.getId(), testCustomer).getStatus())
//                .isEqualTo(Status.RECORD_ALREADY_EXIST);
//    }
//
//    @Test
//    void itShouldNotDeleteCustomer_BecauseRecordDoesntExist() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.empty());
//
//        assertThat(customerServiceUnderTest.deleteCustomer(
//                testCustomer.getId()).getStatus())
//                .isEqualTo(Status.RECORD_DOESNT_EXIST);
//    }
//
//    @Test
//    void itShouldDeleteCustomer() {
//        when(customerRepository.findById(anyLong()))
//                .thenReturn(Optional.of(testCustomer));
//
//        assertThat(customerServiceUnderTest.deleteCustomer(
//                testCustomer.getId()).getStatus())
//                .isEqualTo(Status.RECORD_DELETED_SUCCESSFULLY);
//    }
//}