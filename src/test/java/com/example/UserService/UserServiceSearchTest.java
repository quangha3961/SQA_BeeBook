package com.example.UserService;

import beebooks.StartServer;
import beebooks.dto.UserSearchModel;
import beebooks.entities.User;
import beebooks.service.PagerData;
import beebooks.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class UserServiceSearchTest {

    @Autowired
    private UserService userService;

    // Test 176: Tìm người dùng theo username
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testSearchWithExistingUsername() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword(new BCryptPasswordEncoder().encode("password123"));
        user.setEmail("user1@example.com");
        user.setAddress("123 Đường Láng, Hà Nội");
        userService.saveOrUpdate(user);

        UserSearchModel searchModel = new UserSearchModel();
        searchModel.keyword = "user1";

        PagerData<User> result = userService.search(searchModel);

        assertFalse(result.getData().isEmpty(), "Có người dùng với username 'user1' trong danh sách");
        assertEquals("user1", result.getData().get(0).getUsername(), "Tên người dùng phải khớp");
    }

    // Test 177: Tìm người dùng theo email
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testSearchWithExistingEmail() {
        User user = new User();
        user.setUsername("user2");
        user.setPassword(new BCryptPasswordEncoder().encode("password456"));
        user.setEmail("user2@example.com");
        user.setAddress("456 Nguyễn Trãi, Hà Nội");
        userService.saveOrUpdate(user);

        UserSearchModel searchModel = new UserSearchModel();
        searchModel.keyword = "user2@example.com";

        PagerData<User> result = userService.search(searchModel);

        assertFalse(result.getData().isEmpty(), "Có người dùng với email 'user2@example.com' trong danh sách");
        assertEquals("user2@example.com", result.getData().get(0).getEmail(), "Email phải khớp");
    }

    // Test 178: Tìm người dùng với từ khóa không tồn tại
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testSearchWithNonExistentKeyword() {
        UserSearchModel searchModel = new UserSearchModel();
        searchModel.keyword = "nonexistent";

        PagerData<User> result = userService.search(searchModel);

        assertTrue(result.getData().isEmpty(), "Không có người dùng với từ khóa 'nonexistent' trong danh sách");
    }

    // Test 179: Tìm người dùng với searchModel null
    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel() {
        User user = new User();
        user.setUsername("user3");
        user.setPassword(new BCryptPasswordEncoder().encode("password789"));
        user.setEmail("user3@example.com");
        user.setAddress("789 Lê Lợi, Hà Nội");
        userService.saveOrUpdate(user);

        // Kiểm tra trường hợp searchModel là null
        UserSearchModel searchModel = null;

        // Truyền searchModel null vào search, đồng thời kiểm tra kết quả trả về
        PagerData<User> result = null;
        try {
            result = userService.search(searchModel);
        } catch (Exception e) {
            fail("Lỗi khi gọi search với searchModel là null: " + e.getMessage());
        }

        // Kiểm tra kết quả trả về có phải là danh sách trống
        assertNotNull(result, "Kết quả tìm kiếm không được null");
        assertTrue(result.getData().isEmpty(), "Không có người dùng khi searchModel là null");
    }

    // Test 34: Tìm người dùng với searchModel.keyword = null
    @Test
    @Order(34)
    @Transactional
    @Rollback
    public void testSearchWithNullKeyword_34() {
        User user = new User();
        user.setUsername("user34");
        user.setPassword(new BCryptPasswordEncoder().encode("password34"));
        user.setEmail("user34@example.com");
        user.setAddress("34 Đường Láng, Hà Nội");
        userService.saveOrUpdate(user);

        UserSearchModel searchModel = new UserSearchModel();
        searchModel.keyword = null;

        PagerData<User> result = userService.search(searchModel);
        assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword là null (trả về tất cả)");
    }

    // Test 35: Tìm người dùng với searchModel.keyword = "" (rỗng)
    @Test
    @Order(35)
    @Transactional
    @Rollback
    public void testSearchWithEmptyKeyword_35() {
        User user = new User();
        user.setUsername("user35");
        user.setPassword(new BCryptPasswordEncoder().encode("password35"));
        user.setEmail("user35@example.com");
        user.setAddress("35 Đường Láng, Hà Nội");
        userService.saveOrUpdate(user);

        UserSearchModel searchModel = new UserSearchModel();
        searchModel.keyword = "";

        PagerData<User> result = userService.search(searchModel);
        assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword rỗng (trả về tất cả)");
    }
}