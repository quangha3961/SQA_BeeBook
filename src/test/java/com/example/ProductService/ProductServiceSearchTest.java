package com.example.ProductService;

import beebooks.StartServer;
import beebooks.dto.ProductSearchModel;
import beebooks.entities.Product;
import beebooks.entities.Categories;
import beebooks.service.PagerData;
import beebooks.service.ProductService;
import beebooks.service.CategoriesService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = StartServer.class)
public class ProductServiceSearchTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriesService categoriesService;

    // Test 99: Tìm sản phẩm theo SEO
    @Test
    @Order(1)
    @Transactional
    @Rollback
    public void testSearchWithExistingSeo() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Lập Trình Java");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Mô tả ngắn về sách lập trình Java");
        product.setDetails("Mô tả chi tiết về sách lập trình Java");

        MockMultipartFile avatar = new MockMultipartFile("productAvatar", "avatar.jpg", "image/jpeg", "avatar content".getBytes());
        MockMultipartFile[] pictures = new MockMultipartFile[]{};
        productService.add(product, avatar, pictures);

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.seo = product.getSeo();

        PagerData<Product> result = productService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Có sản phẩm với SEO '" + product.getSeo() + "' trong danh sách");
    }

    // Test 100: Tìm sản phẩm theo từ khóa (title)
    @Test
    @Order(2)
    @Transactional
    @Rollback
    public void testSearchWithExistingKeywordInTitle() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Lập Trình Python");
        product.setPrice(BigDecimal.valueOf(120000));
        product.setShortDes("Mô tả ngắn về sách lập trình Python");
        product.setDetails("Mô tả chi tiết về sách lập trình Python");

        MockMultipartFile avatar = new MockMultipartFile("productAvatar", "avatar.jpg", "image/jpeg", "avatar content".getBytes());
        MockMultipartFile[] pictures = new MockMultipartFile[]{};
        productService.add(product, avatar, pictures);

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = "Python";

        PagerData<Product> result = productService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Có sản phẩm với từ khóa 'Python' trong tiêu đề");
    }

    // Test 101: Tìm sản phẩm theo từ khóa (short_description)
    @Test
    @Order(3)
    @Transactional
    @Rollback
    public void testSearchWithExistingKeywordInShortDescription() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Lập Trình C++");
        product.setPrice(BigDecimal.valueOf(150000));
        product.setShortDes("Mô tả ngắn về sách lập trình C++");
        product.setDetails("Mô tả chi tiết về sách lập trình C++");

        MockMultipartFile avatar = new MockMultipartFile("productAvatar", "avatar.jpg", "image/jpeg", "avatar content".getBytes());
        MockMultipartFile[] pictures = new MockMultipartFile[]{};
        productService.add(product, avatar, pictures);

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = "mô tả ngắn";

        PagerData<Product> result = productService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Có sản phẩm với từ khóa 'mô tả ngắn' trong mô tả ngắn");
    }

    // Test 102:Tìm sản phẩm theo từ khóa (detail_description)
    @Test
    @Order(4)
    @Transactional
    @Rollback
    public void testSearchWithExistingKeywordInDetailDescription() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Lập Trình Ruby");
        product.setPrice(BigDecimal.valueOf(180000));
        product.setShortDes("Mô tả ngắn về sách lập trình Ruby");
        product.setDetails("Mô tả chi tiết về sách lập trình Ruby");

        MockMultipartFile avatar = new MockMultipartFile("productAvatar", "avatar.jpg", "image/jpeg", "avatar content".getBytes());
        MockMultipartFile[] pictures = new MockMultipartFile[]{};
        productService.add(product, avatar, pictures);

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = "chi tiết";

        PagerData<Product> result = productService.search(searchModel);

        Assertions.assertFalse(result.getData().isEmpty(), "Có sản phẩm với từ khóa 'chi tiết' trong mô tả chi tiết");
    }

    // Test 103: Tìm sản phẩm với từ khóa không tồn tại
    @Test
    @Order(5)
    @Transactional
    @Rollback
    public void testSearchWithNonExistentKeyword() {
        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = "abcxyz";

        PagerData<Product> result = productService.search(searchModel);

        Assertions.assertTrue(result.getData().isEmpty(), "Không có sản phẩm với từ khóa 'abcxyz' trong danh sách");
    }

    // Add 13: Tìm sản phẩm với searchModel = null
    @Test
    @Order(13)
    @Transactional
    @Rollback
    public void testSearchWithNullSearchModel() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Test Null SearchModel");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test null searchModel");
        product.setDetails("Test null searchModel");
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        PagerData<Product> result = productService.search(null);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel là null (trả về tất cả)");
    }

    // Add 14: Tìm sản phẩm với searchModel.keyword = null
    @Test
    @Order(14)
    @Transactional
    @Rollback
    public void testSearchWithNullKeyword() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Test Null Keyword");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test null keyword");
        product.setDetails("Test null keyword");
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = null;

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword là null (trả về tất cả)");
    }

    // Add 15: Tìm sản phẩm với searchModel.keyword = ""
    @Test
    @Order(15)
    @Transactional
    @Rollback
    public void testSearchWithEmptyKeyword() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Test Empty Keyword");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test empty keyword");
        product.setDetails("Test empty keyword");
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.keyword = "";

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.keyword rỗng (trả về tất cả)");
    }

    // Add 16: Tìm sản phẩm với searchModel.seo = null
    @Test
    @Order(16)
    @Transactional
    @Rollback
    public void testSearchWithNullSeo() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Test Null Seo");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test null seo");
        product.setDetails("Test null seo");
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.seo = null;

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.seo là null (trả về tất cả)");
    }

    // Add 17: Tìm sản phẩm với searchModel.seo = ""
    @Test
    @Order(17)
    @Transactional
    @Rollback
    public void testSearchWithEmptySeo() throws IOException {
        Product product = new Product();
        product.setTitle("Sách Test Empty Seo");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test empty seo");
        product.setDetails("Test empty seo");
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.seo = "";

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.seo rỗng (trả về tất cả)");
    }

    // Add 18: Tìm sản phẩm với searchModel.categoryId != null
    @Test
    @Order(18)
    @Transactional
    @Rollback
    public void testSearchWithCategoryId() throws IOException {
        Categories category = new Categories();
        category.setName("Test Category 1");
        category.setDescription("Test Description 1");
        categoriesService.saveOrUpdate(category);

        Product product = new Product();
        product.setTitle("Sách Test CategoryId");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test categoryId");
        product.setDetails("Test categoryId");
        product.setCategories(category);
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = category.getId();

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi searchModel.categoryId đúng");
    }

    // Add 19: Tìm sản phẩm với categoryId và keyword cùng lúc
    @Test
    @Order(19)
    @Transactional
    @Rollback
    public void testSearchWithCategoryIdAndKeyword() throws IOException {
        Categories category = new Categories();
        category.setName("Test Category 2");
        category.setDescription("Test Description 2");
        categoriesService.saveOrUpdate(category);

        Product product = new Product();
        product.setTitle("Sách Test CategoryId Keyword");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test categoryId keyword");
        product.setDetails("Test categoryId keyword");
        product.setCategories(category);
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = category.getId();
        searchModel.keyword = "CategoryId Keyword";

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi tìm với cả categoryId và keyword đúng");
    }

    // Add 20: Tìm sản phẩm với categoryId và seo cùng lúc
    @Test
    @Order(20)
    @Transactional
    @Rollback
    public void testSearchWithCategoryIdAndSeo() throws IOException {
        Categories category = new Categories();
        category.setName("Test Category 3");
        category.setDescription("Test Description 3");
        categoriesService.saveOrUpdate(category);

        Product product = new Product();
        product.setTitle("Sách Test CategoryId Seo");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test categoryId seo");
        product.setDetails("Test categoryId seo");
        product.setCategories(category);
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = category.getId();
        searchModel.seo = product.getSeo();

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi tìm với cả categoryId và seo đúng");
    }

    // Add 21: Tìm sản phẩm với categoryId, keyword, seo cùng lúc
    @Test
    @Order(21)
    @Transactional
    @Rollback
    public void testSearchWithCategoryIdKeywordSeo() throws IOException {
        Categories category = new Categories();
        category.setName("Test Category 4");
        category.setDescription("Test Description 4");
        categoriesService.saveOrUpdate(category);

        Product product = new Product();
        product.setTitle("Sách Test All Condition");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test all condition");
        product.setDetails("Test all condition");
        product.setCategories(category);
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = category.getId();
        searchModel.keyword = "All Condition";
        searchModel.seo = product.getSeo();

        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có kết quả khi tìm với categoryId, keyword, seo đúng");
    }

    // Test: setByCategoryId với searchModel = null
    @Test
    @Order(1001)
    @Transactional
    @Rollback
    public void testSetByCategoryId_NullSearchModel() {
        PagerData<Product> result = productService.search(null);
        Assertions.assertNotNull(result, "Kết quả không được null khi searchModel=null");
    }

    // Test: setByCategoryId với searchModel.categoryId = null
    @Test
    @Order(1002)
    @Transactional
    @Rollback
    public void testSetByCategoryId_NullCategoryId() {
        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = null;
        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertNotNull(result, "Kết quả không được null khi categoryId=null");
    }

    // Test: setByCategoryId với searchModel.categoryId != null
    @Test
    @Order(1003)
    @Transactional
    @Rollback
    public void testSetByCategoryId_WithCategoryId() throws IOException {
        // Tạo category và product
        Categories category = new Categories();
        category.setName("Test Category setByCategoryId");
        category.setDescription("Test Desc");
        categoriesService.saveOrUpdate(category);

        Product product = new Product();
        product.setTitle("Sách setByCategoryId");
        product.setPrice(BigDecimal.valueOf(100000));
        product.setShortDes("Test setByCategoryId");
        product.setDetails("Test setByCategoryId");
        product.setCategories(category);
        productService.add(product, new MockMultipartFile("avatar", "a.jpg", "image/jpeg", "a".getBytes()), new MockMultipartFile[]{});

        ProductSearchModel searchModel = new ProductSearchModel();
        searchModel.categoryId = category.getId();
        PagerData<Product> result = productService.search(searchModel);
        Assertions.assertFalse(result.getData().isEmpty(), "Phải có sản phẩm khi tìm theo categoryId hợp lệ");
    }
}