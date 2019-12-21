package com.authapi.demo.service;

import com.authapi.demo.model.User;
import com.authapi.demo.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDto update(UserDto userDto);
}
