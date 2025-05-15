package com.example.UserDetailsService;

import beebooks.StartServer;
import beebooks.entities.User;
import beebooks.service.UserDetailsServiceImpl;
import beebooks.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    // Test: loadUserByUsername với username tồn tại
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testLoadUserByUsername_ExistingUser() {
        // Tạo user test
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        userService.saveOrUpdate(user);

        // Test loadUserByUsername
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Kiểm tra kết quả
        assertNotNull(userDetails, "UserDetails không được null");
        assertEquals("testuser", userDetails.getUsername(), "Username phải khớp");
    }

    // Test: loadUserByUsername với username không tồn tại
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testLoadUserByUsername_NonExistingUser() {
        // Test với username không tồn tại
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistinguser");
        }, "Phải throw UsernameNotFoundException khi username không tồn tại");
    }

    // Test: loadUserByUsername với username null
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testLoadUserByUsername_NullUsername() {
        // Test với username null
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);
        }, "Phải throw UsernameNotFoundException khi username là null");
    }

    // Test: loadUserByUsername với username rỗng
    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testLoadUserByUsername_EmptyUsername() {
        // Test với username rỗng
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("");
        }, "Phải throw UsernameNotFoundException khi username là rỗng");
    }
} 