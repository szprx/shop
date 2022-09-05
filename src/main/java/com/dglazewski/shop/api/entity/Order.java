package com.dglazewski.shop.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> products = new ArrayList<>();

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDate orderDate;
}