package com.example.RoleService;

import beebooks.StartServer;
import beebooks.entities.Role;
import beebooks.service.RoleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class RoleServiceLoadRoleByRoleNameTest {

    @Autowired
    private RoleService roleService;

    @PersistenceContext
    private EntityManager entityManager;

    // Test 131: Tìm role "ADMIN" - Thành công (Pass)
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Admin_Success() {
        // Tạo và lưu role "ADMIN"
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDescription("ADMIN"); // Mô tả giống với name
        //roleService.saveOrUpdate(adminRole);

        Role foundRole = roleService.loadRoleByRoleName("ADMIN");

        assertNotNull(foundRole, "Role ADMIN phải được tìm thấy");
        assertEquals("ADMIN", foundRole.getName(), "Tên role phải là ADMIN");
        assertEquals("ADMIN", foundRole.getDescription(), "Mô tả role phải giống với tên");
    }

    // Test 132: Tìm role "ADMIN" - Thất bại (Fail)
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Admin_Fail() {
        // Không tạo role "ADMIN" trong DB

        Role foundRole = roleService.loadRoleByRoleName("ADMIN22");

        assertNull(foundRole, "Role ADMIN không tồn tại, phải trả về null");
    }

    // Test 133: Tìm role "GUEST" - Thành công (Pass)
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Guest_Success() {
        // Tạo và lưu role "GUEST"
        Role guestRole = new Role();
        guestRole.setName("GUEST");
        guestRole.setDescription("GUEST"); // Mô tả giống với name
        //roleService.saveOrUpdate(guestRole);

        Role foundRole = roleService.loadRoleByRoleName("GUEST");

        assertNotNull(foundRole, "Role GUEST phải được tìm thấy");
        assertEquals("GUEST", foundRole.getName(), "Tên role phải là GUEST");
        assertEquals("GUEST", foundRole.getDescription(), "Mô tả role phải giống với tên");
    }

    // Test 134: Tìm role "GUEST" - Thất bại (Fail)
    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Guest_Fail() {
        // Không tạo role "GUEST" trong DB

        Role foundRole = roleService.loadRoleByRoleName("GUEST2");

        assertNull(foundRole, "Role GUEST không tồn tại, phải trả về null");
    }

    // Test 135: Tìm role với tên null - Thất bại (Fail)
    @Test
    @Order(5)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_NullName_Fail() {
        Role foundRole = roleService.loadRoleByRoleName(null);

        assertNull(foundRole, "Tìm role với tên null phải trả về null");
    }

    // Add26: Tìm role "ADMIN" - Thành công
    @Test
    @Order(6)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Admin_Success_Add26() {
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setDescription("ADMIN");
        // Lưu role vào DB
        assertThrows(javax.persistence.PersistenceException.class, () -> {
            roleService.saveOrUpdate(adminRole);
            entityManager.flush(); // Đảm bảo lỗi được kích hoạt
        }, "Dự kiến sẽ xảy ra PersistenceException khi tạo role trùng tên");
        entityManager.clear(); // Xóa entity lỗi khỏi session để tránh lỗi Hibernate assertion

        Role foundRole = roleService.loadRoleByRoleName("ADMIN");
        assertNotNull(foundRole, "Role ADMIN phải được tìm thấy");
        assertEquals("ADMIN", foundRole.getName(), "Tên role phải là ADMIN");
        assertEquals("ADMIN", foundRole.getDescription(), "Mô tả role phải giống với tên");
    }

    // Add27: Tìm role "ADMIN" - Không tồn tại
    @Test
    @Order(7)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_Admin_NotFound_Add27() {
        Role foundRole = roleService.loadRoleByRoleName("ADMIN1");
        assertNull(foundRole, "Role ADMIN không tồn tại, phải trả về null");
    }

    // Add28: Tìm role với tên null
    @Test
    @Order(8)
    @Transactional
    @Rollback
    public void testLoadRoleByRoleName_NullName_Add28() {
        Role foundRole = roleService.loadRoleByRoleName(null);
        assertNull(foundRole, "Tìm role với tên null phải trả về null");
    }
}