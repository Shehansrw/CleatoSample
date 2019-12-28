package com.authapi.demo.dao;

import com.authapi.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserDao")
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
