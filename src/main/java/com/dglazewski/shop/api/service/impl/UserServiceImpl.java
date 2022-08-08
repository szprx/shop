package com.dglazewski.shop.api.service.impl;

import com.dglazewski.shop.api.dto.UserDto;
import com.dglazewski.shop.api.dto.creator.UserCreatorDto;
import com.dglazewski.shop.api.repository.UserRepository;
import com.dglazewski.shop.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Override
    public UserDto addUser(UserCreatorDto user) {
        return null;
    }

    @Override
    public UserDto updateUser(Long id, UserCreatorDto user) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDto getUser(Long id) {
        return null;
    }
}
