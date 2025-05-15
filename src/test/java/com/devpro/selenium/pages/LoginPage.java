package com.devpro.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Locators
    private By usernameInput = By.id("username");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".TB");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }
    
    public void login(String username, String password) {
        WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(usernameInput));
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(passwordInput));
        WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        
        usernameElement.clear();
        usernameElement.sendKeys(username);
        
        passwordElement.clear();
        passwordElement.sendKeys(password);
        
        loginButtonElement.click();
    }
    
    public boolean isLoggedIn() {
        try {
            // Kiểm tra xem có chuyển đến trang home không
            wait.until(ExpectedConditions.urlContains("/home"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage));
            return errorElement.getText();
        } catch (Exception e) {
            return "";
        }
    }
} 