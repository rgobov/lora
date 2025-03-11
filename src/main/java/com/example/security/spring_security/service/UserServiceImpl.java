package com.example.security.spring_security.service;

import com.example.security.spring_security.model.Role;
import com.example.security.spring_security.model.User;
import com.example.security.spring_security.repositories.RoleRepository;
import com.example.security.spring_security.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(long id, User updateUser) {
        User userFromBase = findById(id);
        if (userFromBase != null) {
            userFromBase.setEmail(updateUser.getEmail());
            userFromBase.setFirstName(updateUser.getFirstName());
            userFromBase.setLastName(updateUser.getLastName());
            userFromBase.setAge(updateUser.getAge());
            if (updateUser.getRoles() != null) {
                userFromBase.getRoles().clear();
                userFromBase.getRoles().addAll(updateUser.getRoles());
            }
            if (updateUser.getPassword() == null || updateUser.getPassword().isEmpty()) {
                userFromBase.setPassword(userFromBase.getPassword());
            } else {
                userFromBase.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            }
            userRepository.save(userFromBase);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void setRolesForUser(User user, Set<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(id -> roleRepository.findById(Long.valueOf(id)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(roles);
    }

    @Override
    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + email + " не найден"));
    }

    // Новый метод для создания пользователя
    @Override
    @Transactional
    public User createUser(User user, Set<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            setRolesForUser(user, roleIds);
        } else {
            user.setRoles(Set.of());
        }
        save(user); // Используем существующий метод save для шифрования пароля
        return user;
    }


    @Override
    @Transactional
    public User updateUser(Long id, String firstName, String lastName, int age, String email, String password, Set<Long> roleIds) {
        User userFromBase = findById(id);
        if (userFromBase != null) {
            userFromBase.setFirstName(firstName);
            userFromBase.setLastName(lastName);
            userFromBase.setAge(age);
            userFromBase.setEmail(email);
            if (password != null && !password.isEmpty()) {
                userFromBase.setPassword(bCryptPasswordEncoder.encode(password));
            }
            if (roleIds != null) {
                setRolesForUser(userFromBase, roleIds);
            }
            userRepository.save(userFromBase);
            return userFromBase;
        }
        throw new UsernameNotFoundException("Пользователь с id " + id + " не найден");
    }

    // Новый метод для получения текущего пользователя
    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return findByEmail(userDetails.getUsername());
        }
        return null;
    }

    // Существующий метод (можно оставить как есть)
    Long getUserId(Long userId) {
        return userRepository.findById(userId).get().getId();
    }
}