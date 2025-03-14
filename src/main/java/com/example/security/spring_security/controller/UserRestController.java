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

@Controller
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("api/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("api/users")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        return userService.createUser(user, roleIds);
    }

    @PutMapping("api/users/{id}")
    @ResponseBody
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        return userService.updateUser(id, user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), user.getPassword(), roleIds);
    }

    @DeleteMapping("api/users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("api/users/current")
    @ResponseBody
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("api/roles")
    @ResponseBody
    public List<Role> getAllRoles() {
        return userService.findAll().stream()
                .flatMap(u -> u.getRoles().stream())
                .distinct()
                .collect(Collectors.toList());
    }


    @GetMapping("/mainPage")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getAllUsers(Model model) {
        return "all_users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}