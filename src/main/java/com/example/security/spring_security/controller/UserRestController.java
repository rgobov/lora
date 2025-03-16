package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("api/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("api/users")
    public User createUser(@RequestBody User user) {
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        return userService.createUser(user, roleIds);
    }

    @PutMapping("api/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        return userService.updateUser(id, user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getPassword(), roleIds);
    }

    @DeleteMapping("api/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("api/users/current")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("api/roles")
    public List<Role> getAllRoles() {
        return userService.findAll().stream()
                .flatMap(u -> u.getRoles().stream())
                .distinct()
                .collect(Collectors.toList());
    }


    }

