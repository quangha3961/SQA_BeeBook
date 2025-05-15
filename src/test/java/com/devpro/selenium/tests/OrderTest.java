package com.devpro.selenium.tests;

import com.devpro.selenium.base.BaseTest;
import com.devpro.selenium.pages.HomePage;
import com.devpro.selenium.pages.BookDetailPage;
import com.devpro.selenium.pages.CartPage;
import com.devpro.selenium.pages.CheckOrderPage;
import com.devpro.selenium.utils.ExcelDataProvider;
import com.devpro.selenium.utils.EmailUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class OrderTest extends BaseTest {
    {
        BASE_URL = "http://localhost:8080/home";
    }

    @ParameterizedTest
    @MethodSource("getOrderTestData")
    public void testOrderFlow(String books, String fullName, String address, String phone, String email) throws Exception {
        HomePage homePage = new HomePage(driver);
        BookDetailPage bookDetailPage = new BookDetailPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckOrderPage checkOrderPage = new CheckOrderPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<String> bookList = Arrays.asList(books.split(";"));
        for (String bookName : bookList) {
            // Tìm kiếm và thêm sách vào giỏ hàng
            homePage.searchBook(bookName.trim());
            homePage.clickFirstBook();
            bookDetailPage.addToCart();
        }

        // Sau khi thêm hết sách, click vào giỏ hàng
        homePage.clickCartButton();

        // Xử lý đặt hàng
        cartPage.fillCustomerInfo(fullName, email, phone, address);
        boolean orderSuccess = cartPage.placeOrder();

        // Kiểm tra kết quả đặt hàng
        if (!orderSuccess) {
            // Nếu đặt hàng thất bại, kiểm tra thông báo lỗi
            try {
                WebElement alert = driver.findElement(By.className("alert"));
                if (alert != null && alert.isDisplayed()) {
                    System.out.println("Test case thất bại: " + alert.getText());
                }
            } catch (Exception e) {
                System.out.println("Không tìm thấy thông báo lỗi");
            }
            return; // Kết thúc test case nếu đặt hàng thất bại
        }

        // Đợi trang chủ load lại hoàn toàn sau khi đặt hàng
        WebDriverWait waitHome = new WebDriverWait(driver, Duration.ofSeconds(15));
        waitHome.until(ExpectedConditions.urlContains("/home"));
        waitHome.until(ExpectedConditions.presenceOfElementLocated(By.className("nav-chinh")));
        System.out.println("Trang chủ đã load lại, chuẩn bị kiểm tra đơn hàng.");

        // Đợi 15 giây để email được gửi
        System.out.println("Đang đợi email được gửi (15 giây)...");
        Thread.sleep(15000);

        // Lấy mã đơn hàng từ email
        String orderCode = EmailUtils.getLatestOrderCode();
        assertNotNull(orderCode, "Không thể lấy mã đơn hàng từ email");
        System.out.println("Mã đơn hàng: " + orderCode);

        // Click vào link Kiểm tra đơn hàng trong menu navigation
        System.out.println("Đang tìm link kiểm tra đơn hàng trong menu...");
        
        // Đợi menu navigation load xong
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("nav-chinh")));
        
        // Thử nhiều cách khác nhau để tìm link
        WebElement checkOrderLink = null;
        int maxRetries = 3;
        int retryCount = 0;
        boolean clickSuccess = false;
        
        while (retryCount < maxRetries && !clickSuccess) {
            try {
                // Cách 1: Dùng Link Text (đơn giản nhất)
                checkOrderLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.linkText("Kiểm tra đơn hàng")));
                System.out.println("Tìm thấy link theo Link Text");
                
                // Kiểm tra xem link có hiển thị và clickable không
                assertTrue(checkOrderLink.isDisplayed(), "Link kiểm tra đơn hàng không hiển thị");
                assertTrue(checkOrderLink.isEnabled(), "Link kiểm tra đơn hàng không clickable");
                
                // Lưu URL hiện tại trước khi click
                String currentUrl = driver.getCurrentUrl();
                System.out.println("URL hiện tại trước khi click: " + currentUrl);
                
                // Thử click bằng JavaScript
                try {
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", checkOrderLink);
                    System.out.println("Đã click vào link kiểm tra đơn hàng bằng JavaScript");
                    
                    // Đợi URL thay đổi
                    wait.until(ExpectedConditions.urlContains("/checkOrder"));
                    
                    // Đợi trang load hoàn toàn
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("code")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkOrderBtn")));
                    
                    clickSuccess = true;
                } catch (Exception e) {
                    System.out.println("Click bằng JavaScript không hoạt động: " + e.getMessage());
                    
                    // Thử click thông thường
                    try {
                        checkOrderLink.click();
                        System.out.println("Đã click vào link kiểm tra đơn hàng thông thường");
                        
                        // Đợi URL thay đổi
                        wait.until(ExpectedConditions.urlContains("/checkOrder"));
                        
                        // Đợi trang load hoàn toàn
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("code")));
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkOrderBtn")));
                        
                        clickSuccess = true;
                    } catch (Exception e2) {
                        System.out.println("Click thông thường không hoạt động: " + e2.getMessage());
                        
                        // Thử click bằng Actions
                        try {
                            Actions actions = new Actions(driver);
                            actions.moveToElement(checkOrderLink).click().perform();
                            System.out.println("Đã click vào link kiểm tra đơn hàng bằng Actions");
                            
                            // Đợi URL thay đổi
                            wait.until(ExpectedConditions.urlContains("/checkOrder"));
                            
                            // Đợi trang load hoàn toàn
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("code")));
                            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkOrderBtn")));
                            
                            clickSuccess = true;
                        } catch (Exception e3) {
                            System.out.println("Click bằng Actions không hoạt động: " + e3.getMessage());
                        }
                    }
                }
                
                if (!clickSuccess) {
                    retryCount++;
                    if (retryCount < maxRetries) {
                        System.out.println("Thử lại lần " + (retryCount + 1) + "...");
                        Thread.sleep(2000); // Đợi 2 giây trước khi thử lại
                    }
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi tìm link: " + e.getMessage());
                retryCount++;
                if (retryCount < maxRetries) {
                    System.out.println("Thử lại lần " + (retryCount + 1) + "...");
                    Thread.sleep(2000);
                }
            }
        }
        
        if (!clickSuccess) {
            fail("Không thể click vào link kiểm tra đơn hàng sau " + maxRetries + " lần thử");
        }
        
        // Kiểm tra xem đã chuyển trang thành công chưa
        String newUrl = driver.getCurrentUrl();
        System.out.println("URL sau khi click: " + newUrl);
        assertTrue(newUrl.contains("/checkOrder"), "Không chuyển được sang trang kiểm tra đơn hàng");
        
        // Đợi trang load và các phần tử xuất hiện
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("code")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("checkOrderBtn")));
        
        // Tìm trường nhập mã đơn hàng
        System.out.println("Đang tìm trường nhập mã đơn hàng...");
        WebElement orderCodeInput = driver.findElement(By.id("code"));
        assertTrue(orderCodeInput.isDisplayed(), "Không tìm thấy trường nhập mã đơn hàng");
        
        // Nhập mã đơn hàng
        System.out.println("Nhập mã đơn hàng: " + orderCode);
        orderCodeInput.clear();
        orderCodeInput.sendKeys(orderCode);
        
        // Tìm và click nút tra cứu
        System.out.println("Đang tìm nút tra cứu đơn hàng...");
        WebElement searchButton = driver.findElement(By.id("checkOrderBtn"));
        assertTrue(searchButton.isDisplayed(), "Không tìm thấy nút tra cứu đơn hàng");
        
        // Click nút tra cứu
        System.out.println("Click nút tra cứu đơn hàng");
        searchButton.click();
        
        // Đợi kết quả tra cứu
        Thread.sleep(2000);
        
        // Kiểm tra kết quả tra cứu
        try {
            WebElement errorMessage = driver.findElement(By.className("alert-danger"));
            if (errorMessage != null && errorMessage.isDisplayed()) {
                fail("Tra cứu đơn hàng thất bại: " + errorMessage.getText());
            }
        } catch (Exception e) {
            // Không tìm thấy thông báo lỗi, có thể là thành công
            System.out.println("Không tìm thấy thông báo lỗi, tra cứu đơn hàng có thể thành công");
        }

        // Đợi 5 giây để đảm bảo quá trình tra cứu hoàn tất
        Thread.sleep(5000);

        // Đợi thêm 10 giây để quan sát kết quả tra cứu đơn hàng (giúp debug, tránh browser đóng ngay)
        Thread.sleep(10000);
    }

    private static Object[][] getOrderTestData() {
        return ExcelDataProvider.getOrderTestData("OrderTestData.xlsx", "OrderSets");
    }
} 