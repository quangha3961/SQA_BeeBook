package com.devpro.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By fullNameInput = By.id("customer_name");
    private By emailInput = By.id("customer_email");
    private By phoneInput = By.id("customer_phone");
    private By addressInput = By.id("customer_address");
    private By orderButton = By.id("cartBtn");
    private By alertMessage = By.className("alert");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillCustomerInfo(String fullName, String email, String phone, String address) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput)).clear();
        driver.findElement(fullNameInput).sendKeys(fullName);
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput)).clear();
        driver.findElement(phoneInput).sendKeys(phone);
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput)).clear();
        driver.findElement(addressInput).sendKeys(address);
        Thread.sleep(500);
    }

    public boolean placeOrder() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
            
            Thread.sleep(5000);
            
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/check-order") || currentUrl.contains("/order-success")) {
                System.out.println("Đặt hàng thành công - Chuyển hướng đến trang xác nhận");
                return true;
            }
            
            try {
                WebElement alert = driver.findElement(alertMessage);
                if (alert != null && alert.isDisplayed()) {
                    String message = alert.getText().toLowerCase();
                    if (message.contains("thành công") || message.contains("success")) {
                        System.out.println("Đặt hàng thành công: " + alert.getText());
                        return true;
                    } else {
                        System.out.println("Đặt hàng thất bại: " + alert.getText());
                        return false;
                    }
                }
            } catch (Exception e) {
                // Không tìm thấy thông báo
            }
            
            Thread.sleep(5000);
            
            currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("/check-order") || currentUrl.contains("/order-success");
            
        } catch (Exception e) {
            System.out.println("Lỗi khi đặt hàng: " + e.getMessage());
            return false;
        }
    }
} 