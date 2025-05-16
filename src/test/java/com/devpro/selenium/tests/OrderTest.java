package com.devpro.selenium.tests;

import com.devpro.selenium.base.BaseTest;
import com.devpro.selenium.pages.HomePage;
import com.devpro.selenium.pages.BookDetailPage;
import com.devpro.selenium.pages.CartPage;
import com.devpro.selenium.pages.CheckOrderPage;
import com.devpro.selenium.utils.EmailReader;
import com.devpro.selenium.utils.ExcelDataProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class OrderTest extends BaseTest {
    {
        BASE_URL = "http://localhost:8080/home";
    }

    @ParameterizedTest
    @MethodSource("getOrderTestData")
    public void testOrderFlow(String books, String fullName, String address, String phone, String email) throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        BookDetailPage bookDetailPage = new BookDetailPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckOrderPage checkOrderPage = new CheckOrderPage(driver);

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
        
        if (orderSuccess) {
            // Kiểm tra đơn hàng
            Thread.sleep(1000); // Giảm thời gian chờ xuống 1 giây
            homePage.clickCheckOrderLink();
            
            // Đọc mã đơn hàng từ email mới nhất
            Thread.sleep(3000); // Giảm thời gian chờ email xuống 3 giây
            String orderCode = EmailReader.getLatestOrderCode();
            if (orderCode != null) {
                checkOrderPage.enterOrderCode(orderCode);
                checkOrderPage.checkOrder();
                String orderStatus = checkOrderPage.getOrderStatus();
                System.out.println("Order Status: " + orderStatus);
            } else {
                System.out.println("Không tìm thấy mã đơn hàng trong email");
            }
        } else {
            System.out.println("Đặt hàng thất bại do dữ liệu không hợp lệ");
        }

        // Thêm thời gian chờ 3 giây trước khi kết thúc test
        Thread.sleep(3000);
    }

    private static Object[][] getOrderTestData() {
        return ExcelDataProvider.getOrderTestData("OrderTestData.xlsx", "OrderSets");
    }
} 