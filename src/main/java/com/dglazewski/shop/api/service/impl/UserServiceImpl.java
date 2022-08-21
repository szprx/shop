package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public DataBaseStatusResponse<User> addUser(User newUser) {
        Optional<User> user = userRepository.findByEmail(newUser.getEmail());
        if (user.isPresent()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_ALREADY_EXIST);
        }
        if (!newUser.isValid()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_HAS_NOT_VALID_FIELDS);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_CREATED_SUCCESSFULLY,
                userRepository.save(User.create(
                        newUser.getEmail(),
                        passwordEncoder.encode(newUser.getPassword()),
                        newUser.getRole()))
        );
    }

    @Override
    public DataBaseStatusResponse<User> updateUser(Long id, User newUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        if (!newUser.isValid()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_HAS_NOT_VALID_FIELDS
            );
        }
        return user
                .map(oldUser -> {
                    User updatedUser = oldUser.updateWith(User.create(
                            newUser.getEmail(),
                            passwordEncoder.encode(newUser.getPassword()),
                            newUser.getRole()));
                    userRepository.save(updatedUser);
                    return new DataBaseStatusResponse<>(
                            Status.RECORD_UPDATED_SUCCESSFULLY,
                            updatedUser);
                })
                .orElse(new DataBaseStatusResponse<>(
                        Status.RECORD_ALREADY_EXIST));
    }

    @Override
    public DataBaseStatusResponse<Object> deleteUser(Long id) {
        return new DataBaseStatusResponse<>(
                Status.RECORD_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataBaseStatusResponse<User> getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new DataBaseStatusResponse<>(
                    Status.RECORD_DOESNT_EXIST);
        }
        return new DataBaseStatusResponse<>(
                Status.RECORD_RETRIEVED_SUCCESSFULLY,
                user.get());
    }
}