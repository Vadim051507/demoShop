package com.example.demoShop.service;

import com.example.demoShop.entity.Role;
import com.example.demoShop.entity.User;
import com.example.demoShop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldLoadUserByEmail() {
        User user = buildUser("test@gmail.com", "Вадим", Role.USER);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        UserDetails details = userService.loadUserByUsername("test@gmail.com");

        assertEquals("test@gmail.com", details.getUsername());
    }

    @Test
    void shouldThrowWhenUserNotFound(){
        when(userRepository.findByEmail("nobody@gmail.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("nobody@gmail.com"));
    }

    @Test
    void shouldHaveCorrectRoleAuthority() {
        User user = buildUser("admin@gmial.com", "Admin", Role.ADMIN);
        when(userRepository.findByEmail("admin@gmail.com")).thenReturn(Optional.of(user));

        UserDetails details = userService.loadUserByUsername("admin@gmail.com");

        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldHaveUserRoleForRegularUser() {
        User user = buildUser("user@gmail.com", "Вадим", Role.USER);
        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));

        UserDetails details = userService.loadUserByUsername("user@gmail.com");

        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    //TODO: Дописати тести

    private User buildUser(String email, String name, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword("hashed_password");
        user.setRole(role);
        return user;
    }
}

