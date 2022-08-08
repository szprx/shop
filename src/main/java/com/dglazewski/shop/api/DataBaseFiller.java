package com.dglazewski.shop.api;

import com.dglazewski.shop.api.repository.CustomerRepository;
import com.dglazewski.shop.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataBaseFiller {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
//    private OrderRepository orderRepository;
//    private ProductRepository productRepository;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
//            userRepository.save(User.create("adam@wp.pl","haslo",RoleEnum.ADMIN));
//            userRepository.save(User.create("lgha.wp.pl","apss",RoleEnum.ADMIN));
//            userRepository.save(User.create("asasd.wp.pl","aps3434s",RoleEnum.CUSTOMER));
//            customerRepository.save(Customer.create("Daro","NBA",userRepository.findByEmail("asasd.wp.pl")));

        };
    }

}
