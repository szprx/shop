package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByName(String name);
    Optional<Customer> findByUserEmail(String email);
    //TODO add this to UserAccountView to fount which user is logged in
}
