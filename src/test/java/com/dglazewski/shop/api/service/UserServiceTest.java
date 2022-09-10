package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userServiceUnderTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        this.userServiceUnderTest = new UserServiceImpl(userRepository, passwordEncoder);

        Long id = 1L;
        String email = "textEmail@gmail.com";
        String password = "testPassword";
        String verificationCode = "verificationCodeTest";
        boolean enabled = true;
        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;

        this.testUser = new User(id, email, password, verificationCode, enabled, roleCustomer);
    }


    @Test
    void itShouldSaveUser() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThat(userServiceUnderTest.saveUser(
                testUser).getStatus())
                .isEqualTo(Status.RECORD_CREATED_SUCCESSFULLY);

    }

    @Test
    void itShouldNotSaveUser_BecauseRecordWithThisEmailAlreadyExist() {
        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(testUser));

        assertThat(userServiceUnderTest.saveUser(
                testUser).getStatus())
                .isEqualTo(Status.RECORD_ALREADY_EXIST);
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUser() {
    }
}