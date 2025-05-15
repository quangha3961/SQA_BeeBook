package com.example.PromotionService;

import beebooks.StartServer;
import beebooks.dto.SearchModel;
import beebooks.entities.Promotion;
import beebooks.service.PagerData;
import beebooks.service.PromotionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class PromotionServiceSearchTest {

    @Autowired
    private PromotionService promotionService;

    // Test 122: Tìm chương trình khuyến mãi có trong csdl
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testSearchWithExistingKeyword() {
        // First, create a promotion to search for
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi 20%");
        promotion.setPercent(20.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        promotionService.saveOrUpdate(promotion);

        SearchModel searchModel = new SearchModel();
        searchModel.keyword = "Khuyến mãi 20%";

        PagerData<Promotion> result = promotionService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Có chương trình khuyến mãi với tên 'Khuyến mãi 20%' trong danh sách");
    }

    // Test 123: Tìm chương trình khuyến mãi không có trong csdl
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testSearchWithNonExistentKeyword() {
        SearchModel searchModel = new SearchModel();
        searchModel.keyword = "abcxyz";

        PagerData<Promotion> result = promotionService.search(searchModel);

        Assertions.assertTrue(result.getData().isEmpty(), "Không có chương trình khuyến mãi với tên 'abcxyz' trong danh sách");
    }

    // Test 124: Tìm theo ID chương trình khuyến mãi có trong csdl
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testSearchById() {
        // First, create a promotion to search for
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi 20%");
        promotion.setPercent(20.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        Promotion savedPromotion = promotionService.saveOrUpdate(promotion);

        SearchModel searchModel = new SearchModel();
        searchModel.id = savedPromotion.getId();

        PagerData<Promotion> result = promotionService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả với ID tồn tại");
        Assertions.assertEquals(savedPromotion.getId(), result.getData().get(0).getId(), "ID kết quả phải khớp");
    }

    // Test 125: Tìm theo ID chương trình khuyến mãi không có trong csdl
    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testSearchByNonExistentId() {
        SearchModel searchModel = new SearchModel();
        searchModel.id = 999; // Assuming ID 999 does not exist

        PagerData<Promotion> result = promotionService.search(searchModel);

        Assertions.assertTrue(result.getData().isEmpty(), "Không có kết quả với ID 999 không tồn tại");
    }

    // Add 1: Tìm khuyến mãi với searchModel = null
    @Test
    @Order(5)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel() {
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi Null SearchModel");
        promotion.setPercent(10.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        promotionService.saveOrUpdate(promotion);

        PagerData<Promotion> result = promotionService.search(null);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel là null (trả về tất cả)");
    }

    // Add 2: Tìm khuyến mãi với searchModel.keyword = null
    @Test
    @Order(6)
    @Transactional
    @Rollback
    public void testSearchWithNullKeyword() {
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi Null Keyword");
        promotion.setPercent(15.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        promotionService.saveOrUpdate(promotion);

        SearchModel searchModel = new SearchModel();
        searchModel.keyword = null;

        PagerData<Promotion> result = promotionService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword là null (trả về tất cả)");
    }

    // Add 3: Tìm khuyến mãi với searchModel.keyword = "" (rỗng)
    @Test
    @Order(7)
    @Transactional
    @Rollback
    public void testSearchWithEmptyKeyword() {
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi Empty Keyword");
        promotion.setPercent(25.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        promotionService.saveOrUpdate(promotion);

        SearchModel searchModel = new SearchModel();
        searchModel.keyword = "";

        PagerData<Promotion> result = promotionService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword rỗng (trả về tất cả)");
    }

    // Add 4: Tìm khuyến mãi với cả id và keyword cùng lúc
    @Test
    @Order(8)
    @Transactional
    @Rollback
    public void testSearchWithIdAndKeyword() {
        Promotion promotion = new Promotion();
        promotion.setName("Khuyến mãi Id Keyword");
        promotion.setPercent(30.0);
        promotion.setStartDate(Date.valueOf("2025-01-01"));
        promotion.setEndDate(Date.valueOf("2025-12-31"));
        Promotion savedPromotion = promotionService.saveOrUpdate(promotion);

        SearchModel searchModel = new SearchModel();
        searchModel.id = savedPromotion.getId();
        searchModel.keyword = "Id Keyword";

        PagerData<Promotion> result = promotionService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi tìm với cả id và keyword đúng");
        Assertions.assertEquals(savedPromotion.getId(), result.getData().get(0).getId(), "ID kết quả phải khớp");
    }
}