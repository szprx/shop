package com.dglazewski.shop.api.entity;

import com.dglazewski.shop.api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CUSTOMERS")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "orders")
    private List<Order> orders = new ArrayList<>();

    public static Customer create(String name, String lastName, String email, String password) {
        return Customer.builder()
                .name(name)
                .lastName(lastName)
                .user(User.create(email, password, RoleEnum.ROLE_CUSTOMER, false))
                .orders(new ArrayList<>())
                .build();
    }

    public Customer updateWith(Customer customer) {
        return Customer.builder()
                .id(this.id)
                .name(customer.getName())
                .lastName(customer.getLastName())
                .user(this.user)
                .orders(customer.getOrders())
                .build();
    }
}