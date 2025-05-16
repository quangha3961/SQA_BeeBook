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
    private By errorMessage = By.className("alert-danger");

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

    public boolean placeOrder() throws InterruptedException {
        // Click nút đặt hàng
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
        
        try {
            // Đợi tối đa 10 giây để xem có chuyển trang không
            Thread.sleep(10000);
            
            // Kiểm tra xem có thông báo lỗi không
            if (driver.findElements(errorMessage).size() > 0) {
                System.out.println("Đặt hàng thất bại: " + driver.findElement(errorMessage).getText());
                return false;
            }
            
            // Kiểm tra xem đã chuyển về trang chủ chưa
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("/home")) {
                System.out.println("Đặt hàng thành công - Đã chuyển về trang chủ");
                return true;
            } else {
                System.out.println("Đặt hàng thất bại - Không chuyển về trang chủ. URL hiện tại: " + currentUrl);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đặt hàng: " + e.getMessage());
            return false;
        }
    }
} 