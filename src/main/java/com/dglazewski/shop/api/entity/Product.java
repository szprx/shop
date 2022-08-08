package com.dglazewski.shop.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "amount")
    private int amount;

    public static Product create(String name, double price, int amount) {
        return Product.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .build();
    }

    public Product updateWith(Product product) {
        return Product.builder()
                .id(this.id)
                .name(product.name)
                .price(product.price)
                .amount(product.amount)
                .build();
    }
}
