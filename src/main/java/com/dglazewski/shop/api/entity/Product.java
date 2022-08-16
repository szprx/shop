package com.dglazewski.shop.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && amount == product.amount && name.equals(product.name) && imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, amount, imageUrl);
    }

    @Column(name = "amount")
    private int amount;
    @Column(name = "image_url")
    private String imageUrl;

    public static Product create(String name, double price, int amount, String imageUrl) {
        return Product.builder()
                .name(name)
                .price(price)
                .amount(amount)
                .imageUrl(imageUrl)
                .build();
    }

    public Product updateWith(Product product) {
        return Product.builder()
                .id(this.id)
                .name(product.getName())
                .price(product.getPrice())
                .amount(product.getAmount())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public boolean isValid() {
        boolean isValid = true;
        if (this.name == null || this.name.trim().equals("")) {
            isValid = false;
        }
        if (this.price < 0.01) {
            isValid = false;
        }
        if (this.amount < 0) {
            isValid = false;
        }
        if (this.imageUrl == null || this.imageUrl.trim().equals("")) {
            isValid = false;
        }
        return isValid;
    }
}