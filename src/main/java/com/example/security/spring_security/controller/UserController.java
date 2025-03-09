package com.example.security.spring_security.controller;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Controller
//
public class UserController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;



    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("allRoles", allRoles);
        return "all_users";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showFormAddUser(Model model) {
        System.out.println("Сработал");
        // Получаем все роли из базы данных
        List<Role> allRoles = roleRepository.findAll();

        // Передаем список ролей в модель
        model.addAttribute("allRoles", allRoles);

        // Передаем пустой объект User для формы
        model.addAttribute("user", new User());

        return "add_user"; // Имя Thymeleaf шаблона
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUser(
            @ModelAttribute("user") User user,
            @RequestParam(value = "roles", required = false) Set<Long> roleIds // Принимаем ID ролей
    ) {
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = roleIds.stream()
                    .map(id -> roleRepository.findById(Long.valueOf(id))) // Ищем роли по ID
                    .filter(Optional::isPresent) // Отфильтровываем пустые Optional
                    .map(Optional::get) // Извлекаем Role из Optional
                    .collect(Collectors.toSet());
            user.setRoles(roles); // Устанавливаем роли для пользователя
        } else {
            user.setRoles(Set.of()); // Если роли не переданы, устанавливаем пустой набор
        }

        userService.save(user); // Сохраняем пользователя
        return "redirect:/admin"; // Перенаправляем на страницу админа
    }


    @GetMapping("/show")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUserById(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/user";
        } else {
            model.addAttribute("user", user);
            return "selected_user";
        }
    }

    @GetMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

//    @PostMapping("/update")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
//        userService.update(id, user);
//        return "redirect:/affterUpdate?id=" + id;
//
//    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(
            @RequestParam("id") Long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("age") int age,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "roles", required = false) Set<Long> roleIds
    ) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        if(password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        if(roleIds != null) {
            Set<Role> roles = roleIds.stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        userService.update(id,user);
        return "redirect:/admin";
    }

    @GetMapping("/affterUpdate")
    public String affterUpdate(Model model, @RequestParam("id") int id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "selected_user"; // Return the view name
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/affterDelite";
    }
    @GetMapping("/affterDelite")
    @PreAuthorize("hasRole('ADMIN')")
    public String affterDelite(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        return "all_users";
    }
    @GetMapping("/user")
    public String ordinarUser(Model model) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByUserName(userDetails.getUsername()));
        return "ordinar_user";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // Имя шаблона (login.html)
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.findByUserName(userDetails.getUsername());
        }
        return null; // или вернуть пустой объект User
    }
}


