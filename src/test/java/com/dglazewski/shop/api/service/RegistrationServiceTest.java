package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Card;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    private RegistrationService underTestRegistrationService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessage mimeMessage;
    @Mock
    private CustomerService customerService;

    private final String registerSiteUrl = "https://test-site/register";
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        this.underTestRegistrationService = new RegistrationServiceImpl(userRepository, passwordEncoder, mailSender, customerService);

        Long id = 1L;
        String name = "testName";
        String lastName = "testLastName";

        String email = "textEmail@gmail.com";
        String password = "testPassword";
        String verificationCode = "verificationCodeTest";
        boolean enabled = false;
        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;

        User user = new User(id, email, password, verificationCode, enabled, roleCustomer);

        List<Order> orders = new ArrayList<>();

        List<Product> products = new ArrayList<>();

        Card card = new Card(id, products);

        testCustomer = new Customer(id, name, lastName, user, orders, card);
    }

    @Test
    void itShouldRegisterCustomer() throws MessagingException, UnsupportedEncodingException {
        when(customerService.saveCustomer(testCustomer))
                .thenReturn(new DataBaseStatusResponse<>(
                        Status.RECORD_CREATED_SUCCESSFULLY,
                        testCustomer));

        when(mailSender.createMimeMessage())
                .thenReturn(mimeMessage);

//        verify(mailSender).send(mimeMessage);

        assertThat(underTestRegistrationService.register(
                testCustomer, registerSiteUrl).getStatus())
                .isEqualTo(Status.RECORD_CREATED_SUCCESSFULLY);
    }

    @Test
    void itShouldNotRegisterCustomer_BecauseRecordWithThisEmailAlreadyExist() throws MessagingException, UnsupportedEncodingException {
        when(customerService.saveCustomer(testCustomer))
                .thenReturn(new DataBaseStatusResponse<>(
                        Status.RECORD_ALREADY_EXIST));

        assertThat(underTestRegistrationService.register(
                testCustomer, registerSiteUrl).getStatus())
                .isEqualTo(Status.RECORD_ALREADY_EXIST);
    }

    @Test
    void itShouldVerifyCustomer() {
        when(userRepository.findByVerificationCode(testCustomer.getUser().getVerificationCode()))
                .thenReturn(Optional.of(testCustomer.getUser()));

        assertThat(underTestRegistrationService.verify(
                testCustomer.getUser().getVerificationCode()).getStatus())
                .isEqualTo(Status.USER_VERIFICATION_SUCCESS);
    }

    @Test
    void itShouldNotVerifyCustomer_BecauseRecordWithThisVerificationCodeDoesntExist() {
        when(userRepository.findByVerificationCode(testCustomer.getUser().getVerificationCode()))
                .thenReturn(Optional.empty());

        assertThat(underTestRegistrationService.verify(
                testCustomer.getUser().getVerificationCode()).getStatus())
                .isEqualTo(Status.USER_VERIFICATION_FAILURE);
    }

    @Test
    void itShouldNotVerifyCustomer_BecauseRecordWithThisVerificationCodeIsAlreadyVerified() {
        testCustomer.getUser().setEnabled(true);
        when(userRepository.findByVerificationCode(testCustomer.getUser().getVerificationCode()))
                .thenReturn(Optional.of(testCustomer.getUser()));

        assertThat(underTestRegistrationService.verify(
                testCustomer.getUser().getVerificationCode()).getStatus())
                .isEqualTo(Status.USER_VERIFICATION_FAILURE);
    }
    //TODO test sending email with proper email sender
}