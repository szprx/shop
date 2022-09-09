package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String email = "testEmail@gmail.com";
        String password = "testPassword";
        String verificationCode = "testVerificationCode";
        boolean enabled = true;
        RoleEnum roleCustomer = RoleEnum.ROLE_CUSTOMER;

        testUser = new User(id, email, password, verificationCode, enabled, roleCustomer);
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        //given
        //when
        User retrievedUser = userRepository.findByEmail(this.testUser.getEmail()).get();
        //then
        assertThat(retrievedUser.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(retrievedUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(retrievedUser.getVerificationCode()).isEqualTo(testUser.getVerificationCode());
        assertThat(retrievedUser.getRole()).isEqualTo(testUser.getRole());
    }

    @Test
    void itShouldNotFindUserByEmail() {
        //given
        //when
        Optional<User> retrievedUser = userRepository.findByEmail("wrongTestEmail@gmail.com");
        //then
        assertThat(retrievedUser).isEmpty();
    }

    @Test
    void itShouldFindUserByVerificationCode() {
        //given
        //when
        User retrievedUser = userRepository.findByVerificationCode(this.testUser.getVerificationCode()).get();
        //then
        assertThat(retrievedUser.getEmail()).isEqualTo(testUser.getEmail());
        assertThat(retrievedUser.getPassword()).isEqualTo(testUser.getPassword());
        assertThat(retrievedUser.getVerificationCode()).isEqualTo(testUser.getVerificationCode());
        assertThat(retrievedUser.getRole()).isEqualTo(testUser.getRole());
    }

    @Test
    void itShouldNotFindUserByVerificationCode() {
        //given
        //when
        Optional<User> retrievedUser = userRepository.findByVerificationCode("wrongTestVerificationCode");
        //then
        assertThat(retrievedUser).isEmpty();
    }
}