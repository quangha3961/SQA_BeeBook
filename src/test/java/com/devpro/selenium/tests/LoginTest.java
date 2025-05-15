package com.devpro.selenium.tests;

import com.devpro.selenium.base.BaseTest;
import com.devpro.selenium.pages.LoginPage;
import com.devpro.selenium.utils.ExcelDataProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
    
    @ParameterizedTest
    @MethodSource("getLoginTestData")
    public void testLogin(String username, String password, String expectedMessage, String testType) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        
        if ("valid".equals(testType)) {
            assertTrue(loginPage.isLoggedIn(), "Login should be successful");
        } else {
            assertEquals(expectedMessage, loginPage.getErrorMessage(), "Error message should match");
        }
    }

    private static Object[][] getLoginTestData() {
        return ExcelDataProvider.getTestData("LoginTestData.xlsx", "Sheet1");
    }
} 