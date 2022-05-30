package com.marcio.tech.api.services;

import com.marcio.tech.api.domain.User;
import com.marcio.tech.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User createUser(UserDTO obj);

    User updateUser(UserDTO obj);

    void excludeById(Integer id);
}
