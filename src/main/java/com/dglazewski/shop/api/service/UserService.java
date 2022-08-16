package com.dglazewski.shop.api.service;


import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.User;

public interface UserService {
    DataBaseStatusResponse<User>createUser(User user);

    DataBaseStatusResponse<User> updateUser(Long id, User user);

    void deleteUser(Long id);

    DataBaseStatusResponse<User> getUser(Long id);
}
