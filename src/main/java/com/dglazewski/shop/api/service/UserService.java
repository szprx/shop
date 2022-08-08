package com.dglazewski.shop.api.service;


import com.dglazewski.shop.api.dto.UserDto;
import com.dglazewski.shop.api.dto.creator.UserCreatorDto;

public interface UserService {
    UserDto addUser(UserCreatorDto user);

    UserDto updateUser(Long id, UserCreatorDto user);

    void deleteUser(Long id);

    UserDto getUser(Long id);
}
