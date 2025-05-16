package com.devpro.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BookDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By addToCartButton = By.className("add-cart"); // Sửa lại selector theo HTML thực tế

    public BookDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    public void addToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        Thread.sleep(1500); // Chờ để dễ quan sát
    }
} 