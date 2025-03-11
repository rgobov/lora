package com.example.security.spring_security.service;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    void save(User user);

    void update(long id, User updateUser);

    void delete(long id);

    void setRolesForUser(User user, Set<Long> roleIds);

    UserDetails getUserDetails();

    User findByEmail(String email);

    // Новые методы из контроллера
    User createUser(User user, Set<Long> roleIds);

    User updateUser(Long id, String firstName, String lastName, int age, String email, String password, Set<Long> roleIds);

    User getCurrentUser();
}