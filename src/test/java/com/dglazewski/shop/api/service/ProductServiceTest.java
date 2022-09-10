package com.dglazewski.shop.api.service;

import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.ProductRepository;
import com.dglazewski.shop.api.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private ProductService underTestProductService;

    @Mock
    private ProductRepository productRepository;

    private Product testProduct;


    @BeforeEach
    void setUp() {
        underTestProductService = new ProductServiceImpl(productRepository);

        Long id = 1L;
        String name = "testName";
        double price = 1.0;
        int amount = 10;
        String imageUrl = "testUrl";

        this.testProduct = new Product(id, name, price, amount, imageUrl);

    }

    @Test
    void itShouldSaveProduct() {
        when(productRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        assertThat(underTestProductService.saveProduct(testProduct).getStatus())
                .isEqualTo(Status.RECORD_CREATED_SUCCESSFULLY);
    }

    @Test
    void itShouldNotSaveProduct_BecauseRecordWithThisNameAlreadyExist() {
        when(productRepository.findByName(anyString()))
                .thenReturn(Optional.of(testProduct));

        assertThat(underTestProductService.saveProduct(testProduct).getStatus())
                .isEqualTo(Status.RECORD_ALREADY_EXIST);
    }

    @Test
    void itShouldGetProduct() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(testProduct));

        assertThat(underTestProductService.getProduct(testProduct.getId()).getStatus())
                .isEqualTo(Status.RECORD_RETRIEVED_SUCCESSFULLY);
    }

    @Test
    void itShouldNotGetProduct_BecauseRecordDoesntExist() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThat(underTestProductService.getProduct(testProduct.getId()).getStatus())
                .isEqualTo(Status.RECORD_DOESNT_EXIST);
    }
    @Test
    void itShouldGetAllProducts() {
        when(productRepository.findAll())
                .thenReturn(List.of(testProduct));
        assertThat(underTestProductService.getAllProducts().getStatus())
                .isEqualTo(Status.RECORD_RETRIEVED_SUCCESSFULLY);
    }

    @Test
    void itShouldUpdateProduct() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(testProduct));
        when(productRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        assertThat(underTestProductService.updateProduct(testProduct.getId(), testProduct).getStatus())
                .isEqualTo(Status.RECORD_UPDATED_SUCCESSFULLY);
    }
    @Test
    void itShouldNotUpdateProduct_BecauseRecordWithThisNameAlreadyExist() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(testProduct));
        when(productRepository.findByName(anyString()))
                .thenReturn(Optional.of(testProduct));

        assertThat(underTestProductService.updateProduct(testProduct.getId(), testProduct).getStatus())
                .isEqualTo(Status.RECORD_ALREADY_EXIST);
    }

    @Test
    void itShouldNotUpdateProduct_BecauseRecordWithThisIdDoesntExist() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThat(underTestProductService.updateProduct(testProduct.getId(), testProduct).getStatus())
                .isEqualTo(Status.RECORD_DOESNT_EXIST);
    }
}