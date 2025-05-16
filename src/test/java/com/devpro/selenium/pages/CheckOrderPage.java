package com.devpro.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckOrderPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By orderCodeInput = By.id("code");
    private By checkOrderButton = By.id("checkOrderBtn");
    private By orderStatusDiv = By.id("orderStatus");

    public CheckOrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void enterOrderCode(String orderCode) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderCodeInput)).clear();
        driver.findElement(orderCodeInput).sendKeys(orderCode);
        Thread.sleep(500);
    }

    public void checkOrder() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(checkOrderButton)).click();
        Thread.sleep(2000);
    }

    public String getOrderStatus() {
        WebElement statusElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderStatusDiv));
        return statusElement.getText();
    }
} 