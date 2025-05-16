package com.devpro.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By searchInput = By.id("keyword"); // Sửa lại id theo HTML thực tế
    private By searchButton = By.className("searchButton"); // Sửa lại className theo HTML thực tế
    private By firstBook = By.cssSelector("#list-product .item:first-child .item-images a"); // Sửa lại selector theo HTML thực tế
    private By cartButton = By.xpath("//div[@class='carts']//button[@type='submit']");
    private By cartLink = By.xpath("//div[@class='carts']//a[@href='/cart/view'][1]");
    private By checkOrderLink = By.xpath("//li/a[@href='/checkOrder']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tăng timeout lên 10 giây
    }

    public void searchBook(String bookName) throws InterruptedException {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(bookName);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        Thread.sleep(1500); // Chờ để dễ quan sát
    }

    public void clickFirstBook() throws InterruptedException {
        WebElement bookElement = wait.until(ExpectedConditions.presenceOfElementLocated(firstBook));
        // Scroll element into view
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookElement);
        Thread.sleep(1000); // Wait for scroll to complete
        // Wait for element to be clickable and click using JavaScript
        wait.until(ExpectedConditions.elementToBeClickable(firstBook));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", bookElement);
        Thread.sleep(1500); // Chờ để dễ quan sát
    }

    public void clickCartButton() throws InterruptedException {
        Thread.sleep(1500);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();
    }

    public void clickCartLinkJS() throws InterruptedException {
        Thread.sleep(1500);
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public void clickCheckOrderLink() throws InterruptedException {
        // Đợi trang chủ load xong
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        // Scroll đến link kiểm tra đơn hàng
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(checkOrderLink));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", link);
        Thread.sleep(1000);
        
        // Click vào link
        wait.until(ExpectedConditions.elementToBeClickable(checkOrderLink));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        
        // Đợi trang kiểm tra đơn hàng load xong
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("code")));
    }
} 