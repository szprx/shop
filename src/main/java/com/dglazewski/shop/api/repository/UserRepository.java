package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);//required when registered, check if email is occupied

    Optional<User> findByVerificationCode(String verificationCode);////required when registered, check if user confirmed his email
}