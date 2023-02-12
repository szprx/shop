package com.dglazewski.shop.api.database.filler;

import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.RoleEnum;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.api.service.RegistrationService;
import com.dglazewski.shop.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataBaseFiller {

    private ProductService productService;
    private UserService userService;
    private RegistrationService registrationService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            productService.saveProduct(Product.create("banana", 1.21, 10, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8a/Banana-Single.jpg/1200px-Banana-Single.jpg"));
            productService.saveProduct(Product.create("apple", 0.82, 21, "https://healthjade.com/wp-content/uploads/2017/10/apple-fruit.jpg"));
            productService.saveProduct(Product.create("orange", 1.23, 101, "https://img.redro.pl/plakaty/orange-fruit-isolate-orange-citrus-on-white-background-whole-orange-fruit-with-leaves-clipping-path-full-depth-of-field-700-250025160.jpg"));
            productService.saveProduct(Product.create("watermelon", 0.82, 21, "https://images.news18.com/ibnlive/uploads/2022/03/watermelon.jpg"));
            productService.saveProduct(Product.create("blueberry", 0.82, 21, "https://www.athinaion-land.gr/wp-content/uploads/2020/07/mirtilo-1024x892.jpg"));
            productService.saveProduct(Product.create("peach", 0.82, 21, "https://static.libertyprim.com/files/familles/peche-large.jpg?1574630286"));
            productService.saveProduct(Product.create("cherry", 0.82, 21, "https://tradevalley.com/media/sellers/335/products/cherry.jpg"));
            productService.saveProduct(Product.create("grapes", 0.82, 21, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrOApseLKSrThxCg0ITCumsNGlZeBlJRXbVfxLZTVy2NyAnT10QkptFgiG92Q74yVhL5c&usqp=CAU"));
            productService.saveProduct(Product.create("gr2apes", 0.82, 21, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrOApseLKSrThxCg0ITCumsNGlZeBlJRXbVfxLZTVy2NyAnT10QkptFgiG92Q74yVhL5c&usqp=CAU"));
            productService.saveProduct(Product.create("grsdapes", 0.82, 21, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrOApseLKSrThxCg0ITCumsNGlZeBlJRXbVfxLZTVy2NyAnT10QkptFgiG92Q74yVhL5c&usqp=CAU"));
            productService.saveProduct(Product.create("garapes", 0.82, 21, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrOApseLKSrThxCg0ITCumsNGlZeBlJRXbVfxLZTVy2NyAnT10QkptFgiG92Q74yVhL5c&usqp=CAU"));
            productService.saveProduct(Product.create("grasdapes", 0.82, 21, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrOApseLKSrThxCg0ITCumsNGlZeBlJRXbVfxLZTVy2NyAnT10QkptFgiG92Q74yVhL5c&usqp=CAU"));

            userService.saveUser(User.create("admin", "admin", RoleEnum.ROLE_ADMIN, true));
            registrationService.registerWithoutVerify(Customer.create("customer","funny","customer","customer"));
        };
    }
}
