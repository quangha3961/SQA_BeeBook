package com.example.SaleorderProductsService;

import beebooks.StartServer;
import beebooks.dto.OrderSearchModel;
import beebooks.entities.Product;
import beebooks.entities.Saleorder;
import beebooks.entities.SaleorderProducts;
import beebooks.service.ProductService;
import beebooks.service.SaleorderProductsService;
import beebooks.service.SaleorderService;
import beebooks.service.PagerData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class SaleorderProductsServiceSearchTest {

    @Autowired
    private SaleorderProductsService saleorderProductsService;

    @Autowired
    private SaleorderService saleorderService;
    @Autowired
    private ProductService productService;

    // Test 148: Tìm sản phẩm đơn hàng theo saleorder_id
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testSearchWithExistingSaleorderId() {
        // Tạo Saleorder
        Saleorder saleorder = new Saleorder();
        saleorder.setCode("ORDER001");
        saleorder.setCustomer_name("Nguyen Van A");
        Saleorder savedSaleorder = saleorderService.saveOrUpdate(saleorder);

        // Tạo Product và đảm bảo rằng tất cả các trường bắt buộc đều có giá trị
        Product product = new Product();
        product.setTitle("Default Product");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setDetails("Default Product Details");  // Đảm bảo rằng "details" không phải null
        product.setShortDes("Default Short Description"); // Đảm bảo rằng "shortDes" không phải null
        product = productService.saveOrUpdate(product);  // Lưu Product vào DB

        // Tạo SaleorderProducts và gán SaleOrder và Product
        SaleorderProducts saleorderProduct = new SaleorderProducts();
        saleorderProduct.setQuality(2);
        saleorderProduct.setTotal(200000.0);
        saleorderProduct.setSaleOrder(savedSaleorder);
        saleorderProduct.setProduct(product);  // Gán Product vào SaleorderProducts

        // Lưu SaleorderProducts
        saleorderProductsService.saveOrUpdate(saleorderProduct);

        // Tìm kiếm sản phẩm đơn hàng theo saleorder_id
        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = savedSaleorder.getId();  // Tìm theo saleorder_id
        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);

        // Kiểm tra kết quả tìm kiếm
        assertFalse(result.getData().isEmpty(), "Có sản phẩm đơn hàng với saleorder_id " + savedSaleorder.getId());
        assertEquals(savedSaleorder.getId(), result.getData().get(0).getSaleOrder().getId(), "Saleorder ID phải khớp");
        assertEquals(product.getId(), result.getData().get(0).getProduct().getId(), "Product ID phải khớp");
    }

    // Test 149: Tìm sản phẩm đơn hàng với saleorder_id không tồn tại
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testSearchWithNonExistentSaleorderId() {
        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = 999; // ID không tồn tại

        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);

        assertTrue(result.getData().isEmpty(), "Không có sản phẩm đơn hàng với saleorder_id 999");
    }

    // Test 150: Tìm sản phẩm đơn hàng với searchModel null
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel() {
        // Tạo Saleorder
        Saleorder saleorder = new Saleorder();
        saleorder.setCode("ORDER002");
        Saleorder savedSaleorder = saleorderService.saveOrUpdate(saleorder);

        // Tạo Product và đảm bảo rằng tất cả các trường bắt buộc đều có giá trị
        Product product = new Product();
        product.setTitle("Product 1");
        product.setPrice(BigDecimal.valueOf(150000));
        product.setDetails("Product 1 Details");  // Đảm bảo rằng "details" không phải null
        product.setShortDes("Product 1 Short Description"); // Đảm bảo rằng "shortDes" không phải null
        product = productService.saveOrUpdate(product);  // Lưu Product vào DB

        // Tạo SaleorderProducts và gán SaleOrder và Product
        SaleorderProducts saleorderProduct = new SaleorderProducts();
        saleorderProduct.setQuality(3);
        saleorderProduct.setTotal(300000.0);
        saleorderProduct.setSaleOrder(savedSaleorder);
        saleorderProduct.setProduct(product);  // Gán Product vào SaleorderProducts

        // Lưu SaleorderProducts
        saleorderProductsService.saveOrUpdate(saleorderProduct);

        // Tìm kiếm khi searchModel là null
        PagerData<SaleorderProducts> result = saleorderProductsService.search(null);

        // Kiểm tra kết quả tìm kiếm
        assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel là null (trả về tất cả)");
        //assertEquals(1, result.getData().size(), "Chỉ có một sản phẩm trong kết quả tìm kiếm");
        assertEquals(savedSaleorder.getId(), result.getData().get(0).getSaleOrder().getId(), "Saleorder ID phải khớp");
        assertEquals(product.getId(), result.getData().get(0).getProduct().getId(), "Product ID phải khớp");
    }

    // Add29: Tìm sản phẩm đơn hàng với searchModel = null
    @Test
    @Order(10)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel_Add29() {
        // Tạo Saleorder
        Saleorder saleorder = new Saleorder();
        saleorder.setCode("ORDER_ADD29");
        Saleorder savedSaleorder = saleorderService.saveOrUpdate(saleorder);

        // Tạo Product
        Product product = new Product();
        product.setTitle("Product Add29");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setDetails("Details Add29");
        product.setShortDes("Short Add29");
        product = productService.saveOrUpdate(product);

        // Tạo SaleorderProducts
        SaleorderProducts saleorderProduct = new SaleorderProducts();
        saleorderProduct.setQuality(1);
        saleorderProduct.setTotal(100000.0);
        saleorderProduct.setSaleOrder(savedSaleorder);
        saleorderProduct.setProduct(product);
        saleorderProductsService.saveOrUpdate(saleorderProduct);

        PagerData<SaleorderProducts> result = saleorderProductsService.search(null);
        assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel là null (trả về tất cả)");
    }

    // Add30: Tìm sản phẩm đơn hàng với orderId đúng
    @Test
    @Order(11)
    @Transactional
    @Rollback
    public void testSearchWithExistingOrderId_Add30() {
        // Tạo Saleorder
        Saleorder saleorder = new Saleorder();
        saleorder.setCode("ORDER_ADD30");
        Saleorder savedSaleorder = saleorderService.saveOrUpdate(saleorder);

        // Tạo Product
        Product product = new Product();
        product.setTitle("Product Add30");
        product.setPrice(BigDecimal.valueOf(200000));
        product.setDetails("Details Add30");
        product.setShortDes("Short Add30");
        product = productService.saveOrUpdate(product);

        // Tạo SaleorderProducts
        SaleorderProducts saleorderProduct = new SaleorderProducts();
        saleorderProduct.setQuality(2);
        saleorderProduct.setTotal(400000.0);
        saleorderProduct.setSaleOrder(savedSaleorder);
        saleorderProduct.setProduct(product);
        saleorderProductsService.saveOrUpdate(saleorderProduct);

        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = savedSaleorder.getId();
        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);
        assertFalse(result.getData().isEmpty(), "Có sản phẩm đơn hàng với orderId đúng");
        assertEquals(savedSaleorder.getId(), result.getData().get(0).getSaleOrder().getId(), "Saleorder ID phải khớp");
    }

    // Add31: Tìm sản phẩm đơn hàng với orderId không tồn tại
    @Test
    @Order(12)
    @Transactional
    @Rollback
    public void testSearchWithNonExistentOrderId_Add31() {
        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = 99999; // ID không tồn tại
        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);
        assertTrue(result.getData().isEmpty(), "Không có sản phẩm đơn hàng với orderId không tồn tại");
    }

    // Test: search với searchModel = null
    @Test
    @Order(1001)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel_Add1001() {
        PagerData<SaleorderProducts> result = saleorderProductsService.search(null);
        Assertions.assertNotNull(result, "Kết quả không được null khi searchModel=null");
    }

    // Test: search với searchModel.orderId = null
    @Test
    @Order(1002)
    @Transactional
    @Rollback
    public void testSearchWithNullOrderId_Add1002() {
        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = null;
        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);
        Assertions.assertNotNull(result, "Kết quả không được null khi orderId=null");
    }

    // Test: search với searchModel.orderId != null
    @Test
    @Order(1003)
    @Transactional
    @Rollback
    public void testSearchWithOrderId_Add1003() {
        // Tạo SaleorderProducts với orderId hợp lệ (giả lập id=1)
        OrderSearchModel searchModel = new OrderSearchModel();
        searchModel.orderId = 1;
        PagerData<SaleorderProducts> result = saleorderProductsService.search(searchModel);
        Assertions.assertNotNull(result, "Kết quả không được null khi orderId hợp lệ");
    }
}