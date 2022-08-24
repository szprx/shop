package com.dglazewski.shop.api.entity;

import com.dglazewski.shop.api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;


    //TODO add email verify https://www.codejava.net/frameworks/spring-boot/email-verification-example
    public static User create(String email, String password, RoleEnum role, boolean enabled) {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .enabled(enabled)
                .build();
    }

    public User updateWith(User newUser) {
        return User.builder()
                .id(this.id)
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .role(this.role)
                .build();
    }
}
