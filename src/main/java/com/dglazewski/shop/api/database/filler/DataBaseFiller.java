package com.dglazewski.shop.api.database.filler;

import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataBaseFiller {

    private ProductService productService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            productService.addProduct(Product.create("banana", 1.21, 10, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Banana-Single.jpg/1200px-Banana-Single.jpg"));
            productService.addProduct(Product.create("apple", 0.82, 21, "https://healthjade.com/wp-content/uploads/2017/10/apple-fruit.jpg"));
//            userRepository.save(User.create("adam@wp.pl","haslo",RoleEnum.ADMIN));
//            userRepository.save(User.create("lgha.wp.pl","apss",RoleEnum.ADMIN));
//            userRepository.save(User.create("asasd.wp.pl","aps3434s",RoleEnum.CUSTOMER));
//            customerRepository.save(Customer.create("Daro","NBA",userRepository.findByEmail("asasd.wp.pl")));

        };
    }

}
