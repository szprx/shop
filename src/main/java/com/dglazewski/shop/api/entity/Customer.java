package com.dglazewski.shop.api.entity;

import com.dglazewski.shop.api.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card shoppingCard;

    public static Customer create(String name, String lastName, String email, String password) {
        return Customer.builder()
                .name(name)
                .lastName(lastName)
                .user(User.create(email, password, RoleEnum.ROLE_CUSTOMER, false))
                .orders(new ArrayList<>())
                .shoppingCard(Card.create())
                .build();
    }

    public Customer updateWith(Customer customer) {
        return Customer.builder()
                .id(this.id)
                .name(customer.getName())
                .lastName(customer.getLastName())
                .user(this.user.updateWith(customer.getUser()))
                .orders(customer.getOrders())
                .shoppingCard(customer.getShoppingCard())
                .build();
    }
}