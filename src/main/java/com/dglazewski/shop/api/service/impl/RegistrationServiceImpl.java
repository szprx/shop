package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.api.service.RegistrationService;
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
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final CustomerService customerService;
    //TODO: change email sender to SendGrid

    @Override
    public DataBaseStatusResponse<Customer> register(Customer newCustomer, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String encodedPassword = passwordEncoder.encode(newCustomer.getUser().getPassword());
        newCustomer.getUser().setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        newCustomer.getUser().setVerificationCode(randomCode);

        DataBaseStatusResponse<Customer> response = customerService.saveCustomer(newCustomer);

        if (response.getStatus() == Status.RECORD_CREATED_SUCCESSFULLY) {
            sendVerificationEmail(newCustomer, siteURL);
        }
        return response;
    }

    @Override
    public void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = customer.getUser().getEmail();
        String fromAddress = "jan.kowalski.shop.app@gmail.com";
        String senderName = "Smart code inc";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Smart code inc.";

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
    public DataBaseStatusResponse<User> verify(String verificationCode) {
        Optional<User> user = userRepository.findByVerificationCode(verificationCode);
        if (user.isEmpty() || user.get().isEnabled()) {
            return new DataBaseStatusResponse<>(
                    Status.USER_VERIFICATION_FAILURE
            );
        }
        user.get().setVerificationCode(null);
        user.get().setEnabled(true);

        return new DataBaseStatusResponse<>(
                Status.USER_VERIFICATION_SUCCESS,
                userRepository.save(user.get()));
    }
    @Override
    public DataBaseStatusResponse<Customer> registerWithoutVerify(Customer newCustomer) {
        String encodedPassword = passwordEncoder.encode(newCustomer.getUser().getPassword());
        newCustomer.getUser().setPassword(encodedPassword);
        newCustomer.getUser().setVerificationCode(null);
        newCustomer.getUser().setEnabled(true);
        return customerService.saveCustomer(newCustomer);

    }
}