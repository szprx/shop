package com.dglazewski.shop.api.repository;

import com.dglazewski.shop.api.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTestProductRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        Long id = 1L;
        String name = "testName";
        double price = 1.0;
        int amount = 10;
        String imageUrl = "testUrl";

        this.testProduct = new Product(id, name, price, amount, imageUrl);
        underTestProductRepository.save(this.testProduct);
    }

    @AfterEach
    void tearDown() {
        underTestProductRepository.deleteAll();
    }

    @Test
    void itShouldFindProductByName() {
        //given
        //when
        Product retrievedProduct = underTestProductRepository.findByName(testProduct.getName()).get();
        //then
        assertThat(retrievedProduct.getName()).isEqualTo(testProduct.getName());
        assertThat(retrievedProduct.getAmount()).isEqualTo(testProduct.getAmount());
        assertThat(retrievedProduct.getPrice()).isEqualTo(testProduct.getPrice());
        assertThat(retrievedProduct.getImageUrl()).isEqualTo(testProduct.getImageUrl());
    }

    @Test
    void itShouldNotFindProductByName_BecauseRecordDoesntExist() {
        //given
        //when
        Optional<Product> retrievedProduct = underTestProductRepository.findByName("wrongTestName");
        //then
        assertThat(retrievedProduct).isEmpty();
    }
}