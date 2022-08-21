package com.dglazewski.shop.api.entity;

import com.dglazewski.shop.api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;

    public static User create(String email, String password, RoleEnum role) {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public boolean isValid() {
        boolean isValid = true;
        if (this.email == null || this.email.trim().equals("")) {
            isValid = false;
        }
        if (this.password == null || this.password.trim().equals("")) {
            isValid = false;
        }
        return isValid;
    }

    public User updateWith(User newUser) {
        return User.builder()
                .id(this.id)
                .email(newUser.getEmail())
                .password(newUser.getPassword())
//                .role(newUser.getRole())
                .role(this.role)
                .build();
    }
}
