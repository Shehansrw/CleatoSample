package com.authapi.demo.dao;

import com.authapi.demo.model.User;

import java.util.List;


public interface UserCriteriaDao {
    public List<User> getUsers();
    public User findByUsername(String username);
}
