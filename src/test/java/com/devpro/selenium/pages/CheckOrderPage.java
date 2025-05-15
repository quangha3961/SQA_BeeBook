package com.devpro.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOrderPage {
    private WebDriver driver;

    @FindBy(id = "code")
    private WebElement orderCodeInput;

    @FindBy(id = "checkOrderBtn")
    private WebElement checkOrderButton;

    public CheckOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void checkOrder(String orderCode) {
        orderCodeInput.sendKeys(orderCode);
        checkOrderButton.click();
    }
} 