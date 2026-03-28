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

    @Test
    void shouldRegisterNewUser() {
        when(userRepository.existsByEmail("new@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.register("Vadim", "new@gmail.com", "+380966008376", "password123");

        assertEquals("Vadim", result.getName());
        assertEquals("new@gmail.com", result.getEmail());
        assertEquals("hashed", result.getPassword());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail("exists@gmail.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.register("Vadim", "exists@gmail.com", "+380966008376", "password123"));
    }

    @Test
    void shouldEncodePasswordOnRegister() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        User result = userService.register("Vadim", "test@gmail.com", "+380966008376", "rawPass");

        assertNotEquals("rawPass", result.getPassword());
        assertEquals("encodedPass", result.getPassword());
    }

    @Test
    void shouldSaveUserToRepository() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        userService.register("Vadim", "test@gmail.com", "+380966008376", "pass");

        verify(userRepository, times(1)).save(any(User.class));
    }

    private User buildUser(String email, String name, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword("hashed_password");
        user.setRole(role);
        return user;
    }
}

