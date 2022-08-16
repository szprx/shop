package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public DataBaseStatusResponse<User> createUser(User user) {
        return null;
    }

    @Override
    public DataBaseStatusResponse<User> updateUser(Long id, User user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public DataBaseStatusResponse<User> getUser(Long id) {
        return null;
    }
}
