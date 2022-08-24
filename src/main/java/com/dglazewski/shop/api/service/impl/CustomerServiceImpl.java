package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.CustomerRepository;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.CustomerService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;

    public DataBaseStatusResponse<Customer> register(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String encodedPassword = passwordEncoder.encode(newCustomer.getUser().getPassword());
        newCustomer.getUser().setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        newCustomer.getUser().setVerificationCode(randomCode);
        newCustomer.getUser().setEnabled(false);
        DataBaseStatusResponse<Customer> response = addCustomer(newCustomer);

        if (response.getStatus().name().equals(Status.RECORD_CREATED_SUCCESSFULLY.name())) {
            sendVerificationEmail(newCustomer, siteURL);
        }
        return response;
    }

    public void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = customer.getUser().getEmail();
        String fromAddress = "jan.kowalski.shop.app@gmail.com";
        String senderName = "SZPRX industries";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", customer.getName() + " " + customer.getLastName());
        String verifyURL = siteURL + "/verify/?code=" + customer.getUser().getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Override
    public DataBaseStatusResponse<Customer> verify(String verificationCode) {
        Optional<Customer> customer = customerRepository.findByUserVerificationCode(verificationCode);
        if (customer.isEmpty() || customer.get().getUser().isEnabled()) {
            return new DataBaseStatusResponse<>(
                    Status.USER_VERIFICATION_FAILURE
            );
        }
        customer.get().getUser().setVerificationCode(null);
        customer.get().getUser().setEnabled(true);

        return new DataBaseStatusResponse<>(
                Status.USER_VERIFICATION_SUCCESS,
                customerRepository.save(customer.get()));
    }

    //TODO delete second Customer.create() in addCustomer and updateCustomer
    @Override
    public DataBaseStatusResponse<Customer> addCustomer(Customer newCustomer) {
        Optional<User> user = userRepository.findByEmail(newCustomer.getUser().getEmail());
        if (user.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                customerRepository.save(Customer.create(
                        newCustomer.getName(),
                        newCustomer.getLastName(),
                        newCustomer.getUser().getEmail(),
                        passwordEncoder.encode(newCustomer.getUser().getPassword()))));
    }

    @Override
    public DataBaseStatusResponse<Customer> updateCustomer(Long id, Customer newCustomer) {
        //TODO add checking if used email is occupied
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return customer
                .map(oldCustomer -> {
                    Customer updatedCustomer = oldCustomer.updateWith(newCustomer);
                    customerRepository.save(updatedCustomer);
                    return new DataBaseStatusResponse<>(
                            Status.RECORD_UPDATED_SUCCESSFULLY,
                            updatedCustomer);
                })
                .orElse(new DataBaseStatusResponse<>(
                        Status.RECORD_ALREADY_EXIST));
    }

    @Override
    public DataBaseStatusResponse<Customer> getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                customer.get());
    }

    @Override
    public DataBaseStatusResponse<Customer> deleteCustomer(Long id) {
        return new DataBaseStatusResponse<>(
                Status.RECORD_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataBaseStatusResponse<Customer> getCustomer(String email) {
        Optional<Customer> customer = customerRepository.findByUserEmail(email);
        if (customer.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                customer.get());
    }
}
