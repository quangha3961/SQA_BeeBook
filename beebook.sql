CREATE DATABASE  IF NOT EXISTS `beebook` ;
USE `beebook`;


DROP TABLE IF EXISTS `tbl_category`;
CREATE TABLE `tbl_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_category` WRITE;
INSERT INTO `tbl_category` VALUES (42,'SÁCH THIẾU NHI',1,'2024-03-26 22:57:51',NULL,NULL,NULL,'sach-thieu-nhi','<p>Thư viện sách</p>'),(43,'VĂN HỌC VIỆT NAM',1,'2024-03-26 22:58:23',NULL,NULL,NULL,'van-hoc-viet-nam','<p>Thư viện sách</p>'),(44,'VĂN HỌC NƯỚC NGOÀI',1,'2024-03-26 22:58:48',NULL,NULL,NULL,'van-hoc-nuoc-ngoai','<p>Thư viện sách<br></p>'),(45,'SÁCH GIÁO KHOA',1,'2024-03-26 23:15:42',NULL,NULL,NULL,'sach-giao-khoa','<p>Thư viện sách</p>'),(46,'SÁCH KINH TẾ',1,'2024-03-26 23:33:41',NULL,NULL,NULL,'sach-kinh-te','<p>Thư viện sách</p>'),(47,'SÁCH NGOẠI NGỮ',1,'2024-03-26 23:34:06',NULL,NULL,NULL,'sach-ngoai-ngu','<p>Thư viện sách<br></p>');

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_category_blog`;

CREATE TABLE `tbl_category_blog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `seo` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_category_blog` WRITE;

INSERT INTO `tbl_category_blog` VALUES (1,NULL,'2024-04-16 13:36:08.196000',_binary '',NULL,'2024-04-16 13:52:17.188000','<p>Mẹo nấu ăn hàng ngày cho các bạn!!!!!</p>','Mẹo nấu ăn','meo-nau-an');

UNLOCK TABLES;

DROP TABLE IF EXISTS `tbl_contact`;

CREATE TABLE `tbl_contact` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `massage` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_contact` WRITE;
INSERT INTO `tbl_contact` VALUES (37,'Ronaldo','ronaldo@gmail.com','welcom!!',1,'2023-06-07 01:39:41',NULL,NULL,NULL),(38,'Nam','namhai342@gmail.com','Shop cố gắng nhập thêm sản phẩm nhé!',1,'2023-06-09 09:13:12',NULL,NULL,NULL),(41,'Nguyễn Gia Thiều','giathieu123@gmail.com','Cải thiện nhiều hơn nhé',1,'2023-07-28 09:35:44',NULL,NULL,NULL);

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_products`;

CREATE TABLE `tbl_products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `short_description` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `detail_description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `price` decimal(13,2) NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `price_sale` decimal(13,2) DEFAULT NULL,
  `author` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `publication_year` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `publisher` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `release_date` datetime(6) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_1tbl_category_Ntbl_products_idx` (`category_id`),
  CONSTRAINT `fk_1tbl_category_Ntbl_products` FOREIGN KEY (`category_id`) REFERENCES `tbl_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=692 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_products` WRITE;
INSERT INTO `tbl_products` VALUES (689,'THÁM TỬ CONAN','Mở đầu câu truyện, cậu học sinh trung học 16 tuổi Shinichi Kudo bị biến thành cậu bé Conan Edogawa. Shinichi trong phần đầu của Thám tử lừng danh Conan được miêu tả là một thám tử học đường. Trong một lần đi chơi công viên \"Miền Nhiệt đới\" với cô bạn từ thuở nhỏ (cũng là bạn gái) Ran Mori , cậu bị dính vào vụ án một hành khách trên Chuyến tàu tốc hành trong công viên, Kishida , bị giết trong một vụ án cắt đầu rùng rợn. Cậu đã làm sáng tỏ vụ án và trên đường về nhà, chứng kiến một vụ làm ăn mờ ám của những người đàn ông mặc toàn đồ đen. Kudo bị phát hiện, bị đánh ngất sau đó những người đàn ông áo đen đã cho cậu uống một thứ thuốc độc chưa qua thử nghiệm là Apotoxin-4869 (APTX4869) với mục đích thủ tiêu cậu. Tuy nhiên chất độc đã không giết chết Kudo. Khi tỉnh lại, cậu bàng hoàng nhận thấy thân thể mình đã bị teo nhỏ trong hình dạng của một cậu học sinh tiểu học....','<p><span style=\"color: rgb(51, 51, 51); font-family: Tahoma, sans-serif, Helvetica, Arial; font-size: 14px; background-color: rgb(249, 249, 249);\">Mở đầu câu truyện, cậu học sinh trung học 16 tuổi Shinichi Kudo bị biến thành cậu bé Conan Edogawa. Shinichi trong phần đầu của Thám tử lừng danh Conan được miêu tả là một thám tử học đường. Trong một lần đi chơi công viên \"Miền Nhiệt đới\" với cô bạn từ thuở nhỏ (cũng là bạn gái) Ran Mori , cậu bị dính vào vụ án một hành khách trên Chuyến tàu tốc hành trong công viên, Kishida , bị giết trong một vụ án cắt đầu rùng rợn. Cậu đã làm sáng tỏ vụ án và trên đường về nhà, chứng kiến một vụ làm ăn mờ ám của những người đàn ông mặc toàn đồ đen. Kudo bị phát hiện, bị đánh ngất sau đó những người đàn ông áo đen đã cho cậu uống một thứ thuốc độc chưa qua thử nghiệm là Apotoxin-4869 (APTX4869) với mục đích thủ tiêu cậu. Tuy nhiên chất độc đã không giết chết Kudo. Khi tỉnh lại, cậu bàng hoàng nhận thấy thân thể mình đã bị teo nhỏ trong hình dạng của một cậu học sinh tiểu học....</span><br></p>',20000.00,1,NULL,'2024-04-16 17:33:03',NULL,1,44,'product/avatar/conan.jpg','tham-tu-conan',NULL,'Gosho Aoyama','2010','NXB Kim Đồng','2024-03-01 00:00:00.000000',100),(691,'DORAEMON','Lần này, nhóm bạn Nobita đã phiêu lưu tới một hành tinh cổ tích. Những gì đang chờ đợi nhóm bạn ở thế giới kì diệu ấy?… Mời tất cả các em cùng dõi theo hành trình đầy mạo hiểm này!','<p><span style=\"color: rgb(51, 51, 51); font-family: Muli, Arial, sans-serif; font-size: 14px;\">Lần này, nhóm bạn Nobita đã phiêu lưu tới một hành tinh cổ tích. Những gì đang chờ đợi nhóm bạn ở thế giới kì diệu ấy?… Mời tất cả các em cùng dõi theo hành trình đầy mạo hiểm này!</span><br></p>',22000.00,1,'2024-04-08 00:08:52',NULL,NULL,NULL,42,'product/avatar/doraemon.jpg','doraemon',NULL,'Fujiko F Fujio','2023','Nhà Xuất Bản Kim Đồng','2024-04-08 00:00:00.000000',50);

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_products_images`;

CREATE TABLE `tbl_products_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `product_id` int NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_1tbl_products_Ntbl_products_images_idx` (`product_id`),
  CONSTRAINT `FKjac7pn534bktj4tvkxqvydglf` FOREIGN KEY (`product_id`) REFERENCES `tbl_products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_products_images` WRITE;
INSERT INTO `tbl_products_images` VALUES (78,'doraemon.jpg','product/pictures/doraemon.jpg',691,1,NULL,NULL,NULL,NULL),(79,'conan.jpg','product/pictures/conan.jpg',689,1,NULL,NULL,NULL,NULL);

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_roles`;

CREATE TABLE `tbl_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `description` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_roles` WRITE;
INSERT INTO `tbl_roles` VALUES (3,'ADMIN',NULL,NULL,NULL,NULL,NULL,'ADMIN'),(4,'GUEST',NULL,NULL,NULL,NULL,NULL,'GUEST');
UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_users`;

CREATE TABLE `tbl_users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `phone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `UKj562wwmipqt96rkoqbo0jc34` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_users` WRITE;

INSERT INTO `tbl_users` VALUES (30,'admin',1,'2023-10-30 11:02:42',NULL,NULL,NULL,'$2a$04$DjcHdBp4E6qi/a5h94FbQ.FGYqIUPKp1nDxIw1nBFOCF7dDMp14Nq','admin123@gmail.com','Hà Nội','0123654789'),(31,'user',1,'2023-10-30 11:03:13',NULL,NULL,NULL,'$2a$04$SoUOcXW1nfOAxuyOjL1eC.YHIXcelB8Sp6SbJIQZfepE5PZfS.cXK','user123@gmai.com','Thành phố Hồ Chí Minh','0123556889'),(32,'hieu',1,'2023-11-29 15:00:05',NULL,NULL,NULL,'$2a$04$6278L6du7mYSCrCeeUTOJ.Ke57ZcScwrZ9EHsQM9r2YHz0CoOaWY.','duchieu1370@gmail.com','Số 2, Ngõ 50, Ngách 142/4, Mễ Trì Thượng','0367533485');

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_users_roles`;

CREATE TABLE `tbl_users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_1tbl_roles_Ntbl_users_roles_idx` (`role_id`),
  CONSTRAINT `fk_1tbl_roles_Ntbl_users_roles` FOREIGN KEY (`role_id`) REFERENCES `tbl_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_1tbl_users_Ntbl_users_roles` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

LOCK TABLES `tbl_users_roles` WRITE;
INSERT INTO `tbl_users_roles` VALUES (30,3),(31,4),(32,4);

UNLOCK TABLES;

DROP TABLE IF EXISTS `tbl_saleorder`;

CREATE TABLE `tbl_saleorder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_id` int DEFAULT NULL,
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `customer_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `customer_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `customer_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `total` decimal(13,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `payment_type` int DEFAULT NULL COMMENT 'Kiểu thanh toán',
  `payment_status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_1tbl_users_Ntbl_saleorder` (`user_id`),
  CONSTRAINT `FKbiu8ui4krw8j3gtn97w3rdq7v` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



LOCK TABLES `tbl_saleorder` WRITE;
INSERT INTO `tbl_saleorder` VALUES (36,'1711736763641',NULL,'John','','','',NULL,1,'2024-03-30 01:26:04',NULL,NULL,NULL,NULL,1,1),(37,'1711737039965',NULL,'bbg','bgfb','343','gbd@g',NULL,1,'2024-03-30 01:30:40',NULL,NULL,NULL,NULL,1,1),(38,'1712509568326',NULL,'NguyễnĐức Hiếu','Hà Nội','0367533485','duchieu1370@gmail.com',NULL,1,'2024-04-08 00:06:08',NULL,NULL,NULL,NULL,1,1),(39,'1713166078106',NULL,'NguyễnĐức Hiếu','Hà Nội','0367533485','duchieu1370@gmail.com',NULL,1,'2024-04-15 14:27:58',NULL,NULL,NULL,NULL,1,1),(40,'1713166599676',NULL,'NguyễnĐức Hiếu','Hà Nội','0367533485','duchieu1370@gmail.com',NULL,1,'2024-04-15 14:36:40',NULL,NULL,NULL,NULL,1,1),(41,'1713327382753',NULL,'Nguyễn Đức Hiếu','Hà Nội','0367533485','duchieu1370@gmail.com',NULL,1,'2024-04-17 11:16:23',NULL,1,NULL,NULL,1,1);

UNLOCK TABLES;



DROP TABLE IF EXISTS `tbl_saleorder_products`;
CREATE TABLE `tbl_saleorder_products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `saleorder_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quality` int NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnyfdau9vc46jkiwvrgj1ns85v` (`product_id`),
  KEY `FKnpyir3q973iv4wq49ltw0kcrd` (`saleorder_id`),
  CONSTRAINT `FKnpyir3q973iv4wq49ltw0kcrd` FOREIGN KEY (`saleorder_id`) REFERENCES `tbl_saleorder` (`id`),
  CONSTRAINT `FKnyfdau9vc46jkiwvrgj1ns85v` FOREIGN KEY (`product_id`) REFERENCES `tbl_products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_saleorder_products` WRITE;
INSERT INTO `tbl_saleorder_products` VALUES (53,38,689,1,1,NULL,NULL,NULL,NULL),(54,39,689,1,1,NULL,NULL,NULL,NULL),(55,40,689,1,1,NULL,NULL,NULL,NULL),(56,41,689,2,1,NULL,NULL,NULL,NULL);
UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_subcribe`;

CREATE TABLE `tbl_subcribe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` int DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_subcribe` WRITE;

INSERT INTO `tbl_subcribe` VALUES (79,'hihi@gmail.com',1,'2023-03-06 14:43:32',NULL,NULL,NULL),(93,'Messi333@gmail.com',1,'2023-06-07 01:41:13',NULL,NULL,NULL),(94,'hai2244@gmail.com',1,'2023-06-09 09:10:24',NULL,NULL,NULL),(95,'trieuanh137@gmail.com',1,'2023-06-19 08:01:26',NULL,NULL,NULL);

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_blog`;
CREATE TABLE `tbl_blog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail_description` longtext COLLATE utf8mb4_bin NOT NULL,
  `seo` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `short_description` varchar(3000) COLLATE utf8mb4_bin NOT NULL,
  `title` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `category_blog_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKacv04twhvx3lfcinfgiorumoe` (`category_blog_id`),
  CONSTRAINT `FKacv04twhvx3lfcinfgiorumoe` FOREIGN KEY (`category_blog_id`) REFERENCES `tbl_category_blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


LOCK TABLES `tbl_blog` WRITE;
INSERT INTO `tbl_blog` VALUES (1,NULL,'2024-04-16 13:53:37.643000',_binary '',1,'2024-04-16 16:20:01.411000','product/avatar/cay-cam-ngot-cua-toi.jpg','<p data-sourcepos=\"3:1-3:253\" style=\"box-sizing: inherit; margin-bottom: 1.25rem; font-family: &quot;Helvetica Neue&quot;, Helvetica, Roboto, Arial, sans-serif; font-size: 16px; line-height: 1.6; text-rendering: optimizelegibility; color: rgb(34, 34, 34); text-align: justify;\">là câu chuyện về cậu bé Zezé, một cậu bé 5 tuổi thông minh, tinh nghịch và đáng yêu, sinh ra và lớn lên trong một gia đình nghèo đông con ở vùng ngoại ô Rio de Janeiro, Brazil. Cậu bé có ước mơ trở thành một nhà thơ cổ thắt nơ bướm.</p><p data-sourcepos=\"5:1-5:248\" style=\"box-sizing: inherit; margin-bottom: 1.25rem; font-family: &quot;Helvetica Neue&quot;, Helvetica, Roboto, Arial, sans-serif; font-size: 16px; line-height: 1.6; text-rendering: optimizelegibility; color: rgb(34, 34, 34); text-align: justify;\">Cuộc sống của Zezé không hề dễ dàng. Cậu phải chịu đựng sự hắt hủi, đánh mắng của cha mẹ và anh chị em. Cậu cũng phải đối mặt với những khó khăn của cuộc sống nghèo khó. Tuy nhiên, Zezé vẫn giữ được trái tim trong sáng và trí tưởng tượng phong phú.</p><p data-sourcepos=\"7:1-7:227\" style=\"box-sizing: inherit; margin-bottom: 1.25rem; font-family: &quot;Helvetica Neue&quot;, Helvetica, Roboto, Arial, sans-serif; font-size: 16px; line-height: 1.6; text-rendering: optimizelegibility; color: rgb(34, 34, 34); text-align: justify;\">Cậu bé tìm thấy một người bạn đặc biệt, đó là cây cam ngọt trong vườn sau nhà. Cậu bé thường tâm sự với cây cam về những niềm vui, nỗi buồn của mình. Cây cam ngọt cũng trở thành nguồn động viên, khích lệ cậu bé trong cuộc sống.</p><p data-sourcepos=\"9:1-9:198\" style=\"box-sizing: inherit; margin-bottom: 1.25rem; font-family: &quot;Helvetica Neue&quot;, Helvetica, Roboto, Arial, sans-serif; font-size: 16px; line-height: 1.6; text-rendering: optimizelegibility; color: rgb(34, 34, 34); text-align: justify;\">Một ngày kia, Zezé gặp gỡ một người bạn mới, đó là Lọ Lem. Lọ Lem là một cậu bé mồ côi, sống lang thang trên đường phố. Lọ Lem đã giúp Zezé khám phá thế giới bên ngoài và dạy cậu bé cách yêu thương.</p><p data-sourcepos=\"11:1-11:227\" style=\"box-sizing: inherit; margin-bottom: 1.25rem; font-family: &quot;Helvetica Neue&quot;, Helvetica, Roboto, Arial, sans-serif; font-size: 16px; line-height: 1.6; text-rendering: optimizelegibility; color: rgb(34, 34, 34); text-align: justify;\">Cuộc sống của Zezé đã thay đổi mãi mãi sau khi cậu gặp Lọ Lem. Cậu bé học được cách đối mặt với những khó khăn và vượt qua những thử thách trong cuộc sống. Cậu cũng học được cách yêu thương và trân trọng những người xung quanh.</p>','cay-cam-ngot-cua-toi-tieng-long-tre-tho','Sách \"Cây cam ngọt của tôi\" là tự truyện về thời thơ ấu của cậu bé Zézé, mang bài học về sự thấu cảm và lòng trắc ẩn.','\'Cây cam ngọt của tôi\' - tiếng lòng trẻ thơ',1),(3,1,'2024-04-16 15:00:53.224000',_binary '',1,'2024-04-16 16:22:44.853000','product/avatar/thien-tai-ben-trai-ke-dien-ben-phai.jpg','<p style=\"box-sizing: inherit; color: rgb(17, 17, 17); font-family: SanFrancisco; font-size: 20px;\">NẾU MỘT NGÀY ANH THẤY TÔI ĐIÊN, THỰC RA CHÍNH LÀ ANH ĐIÊN ĐẤY!</p><p style=\"box-sizing: inherit; color: rgb(17, 17, 17); font-family: SanFrancisco; font-size: 20px;\">Hỡi những con người đang oằn mình trong cuộc sống, bạn biết gì về thế giới của mình? Là vô vàn thứ lý thuyết được các bậc vĩ nhân kiểm chứng, là luật lệ, là cả nghìn thứ sự thật bọc trong cái lốt hiển nhiên, hay những triết lý cứng nhắc của cuộc đời?</p><p style=\"box-sizing: inherit; color: rgb(17, 17, 17); font-family: SanFrancisco; font-size: 20px;\">Lại đây, vượt qua thứ nhận thức tẻ nhạt bị đóng kín bằng con mắt trần gian, khai mở toàn bộ suy nghĩ, để dòng máu trong bạn sục sôi trước những điều kỳ vĩ, phá vỡ mọi quy tắc. Thế giới sẽ gọi bạn là kẻ điên, nhưng vậy thì có sao? Ranh giới duy nhất giữa kẻ điên và thiên tài chẳng qua là một sợi chỉ mỏng manh: Thiên tài chứng minh được thế giới của mình, còn kẻ điên chưa kịp làm điều đó. Chọn trở thành một kẻ điên để vẫy vùng giữa nhân gian loạn thế hay khóa hết chúng lại, sống mãi một cuộc đời bình thường khiến bạn cảm thấy hạnh phúc hơn?</p><p style=\"box-sizing: inherit; color: rgb(17, 17, 17); font-family: SanFrancisco; font-size: 20px;\">Thiên tài bên trái, kẻ điên bên phải là cuốn sách dành cho những người điên rồ, những kẻ gây rối, những người chống đối, những mảnh ghép hình tròn trong những ô vuông không vừa vặn… những người nhìn mọi thứ khác biệt, không quan tâm đến quy tắc. Bạn có thể đồng ý, có thể phản đối, có thể vinh danh hay lăng mạ họ, nhưng điều duy nhất bạn không thể làm là phủ nhận sự tồn tại của họ. Đó là những người luôn tạo ra sự thay đổi trong khi hầu hết con người chỉ sống rập khuôn như một cái máy. Đa số đều nghĩ họ thật điên rồ nhưng nếu nhìn ở góc khác, ta lại thấy họ thiên tài. Bởi chỉ những người đủ điên nghĩ rằng họ có thể thay đổi thế giới mới là những người làm được điều đó.</p><p style=\"box-sizing: inherit; color: rgb(17, 17, 17); font-family: SanFrancisco; font-size: 20px;\">Chào mừng đến với thế giới của những kẻ điên.</p>','cuon-sach-thien-tai-ben-trai-ke-ien-ben-phai','Cuốn sách này nói về những mẫu chuyện giữa cuộc đối thoại tác giả và những người mà chúng ta thường hay gọi đó là bệnh nhân tâm thần. Với quan điểm của tôi cuốn sách nên đọc qua một lần trong đời để có cái nhìn trực quan hơn về các bệnh nhân tâm thần.',' CUỐN SÁCH THIÊN TÀI BÊN TRÁI – KẺ ĐIÊN BÊN PHẢI.',1),(4,1,'2024-04-16 15:03:49.619000',_binary '',1,'2024-04-16 16:25:04.280000','product/avatar/Nhà_giả_kim_(sách).jpg','<h2 style=\"margin: 0cm 0cm 18pt; text-indent: 36pt; line-height: 22.5pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;\"><p style=\"margin-bottom: 20px; max-width: 100%; color: rgb(10, 7, 7); font-family: Roboto; font-size: 16px; text-indent: 0px; text-align: justify; outline: none !important;\">Nhà giả kim (The Alchemist) là một trong những cuốn sách nổi tiếng của nhà văn Paulo Coelho người Bồ Đào Nha, cuốn sách được bán chạy chỉ sau kinh thánh (The Bible), Nhà giả kim là cuốn sách được người đọc săn đón nhiều nhất trong lịch sử văn học thế giới, người ta nói với nhau rằng mỗi sinh viên ở Harvard đều mang theo cuốn sách này như cuốn sách gối đầu giường, một cuốn cẩm nang về kĩ năng sống trong cuộc đời khi mỗi người gặp phải những khó khăn, thử thách chông gai trong thế giới hiện đại.</p><p style=\"margin-bottom: 20px; max-width: 100%; color: rgb(10, 7, 7); font-family: Roboto; font-size: 16px; text-indent: 0px; outline: none !important;\">Cuốn sách Nhà giả kim được rất nhiều các bạn độc giả yêu thích bởi nó lan truyền một thông điệp “Đừng bao giờ từ bỏ ước mơ” trong một câu chuyện thấm đẫm minh triết và sâu sắc của phương Đông. Những triết lý này cũng rất gần gũi với triết lý mà Năng Đoạn Kim Cương muốn nói đến.</p></h2>','nha-gia-kim-cua-paulo-coelho','Vì sao giới trẻ mê mẩn sách \"Nhà giả kim\" của Paulo Coelho','\"Nhà giả kim\" của Paulo Coelho',1),(5,1,'2024-04-16 15:05:02.381000',_binary '',1,'2024-04-16 16:26:24.947000','product/avatar/camonnguoilon.jpg','<p style=\"margin-top: 1.6rem; margin-right: auto; margin-left: auto; color: rgb(52, 58, 64); font-family: arial, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 18px; text-align: justify;\"><em>Cảm Ơn Người Lớn</em>&nbsp;là câu chuyện tuổi thơ được kể với ngôi thứ nhất dưới góc nhìn của Mùi. Mùi đi qua miền ký ức với ba đứa bạn thân là Hải cò, Tí sún và mối tình đầu - Tủn. Câu chuyện xoay quanh những nhân vật có cái tên ngộ nghĩnh mà thân quen ấy cuối cùng lại là tấm vé đưa độc giả chúng ta về lại với tuổi thơ.</p><p style=\"margin-top: 1.6rem; margin-right: auto; margin-left: auto; color: rgb(52, 58, 64); font-family: arial, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 18px; text-align: justify;\">Đôi lúc ta cũng có thể bắt gặp các triết lý trong&nbsp;<em>Cảm Ơn Người Lớn</em>. Đó có thể là chút suy ngẫm về hôn nhân, về thời gian, và đâu đó là về tiền bạc. Tất cả những vấn đề như thuộc về thế giới người lớn ấy đều được tác giả Nguyễn Nhật Ánh “mã hoá” thành ngôn ngữ và góc nhìn của một đứa trẻ. Có lẽ cũng chính vì vậy, khi đọc truyện ta cứ ngỡ đang nhìn về với bản thân năm tám tuổi.</p>','cam-on-nguoi-lon','Cảm ơn người lớn – Cuốn bí kíp về lòng bao dung giữa hai thế hệ','Cảm ơn người lớn',1);

UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_blog_images`;
CREATE TABLE `tbl_blog_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` int DEFAULT NULL,
  `updated_date` datetime(6) DEFAULT NULL,
  `path` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `title` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `blog_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc6sowbfwd9c60y1wky0p40fhv` (`blog_id`),
  CONSTRAINT `FKc6sowbfwd9c60y1wky0p40fhv` FOREIGN KEY (`blog_id`) REFERENCES `tbl_blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



LOCK TABLES `tbl_blog_images` WRITE;
INSERT INTO `tbl_blog_images` VALUES (7,NULL,NULL,_binary '',NULL,NULL,'product/pictures/cay-cam-ngot-cua-toi.jpg','cay-cam-ngot-cua-toi.jpg',1),(8,NULL,NULL,_binary '',NULL,NULL,'product/pictures/thien-tai-ben-trai-ke-dien-ben-phai.jpg','thien-tai-ben-trai-ke-dien-ben-phai.jpg',3),(9,NULL,NULL,_binary '',NULL,NULL,'product/pictures/Nhà_giả_kim_(sách).jpg','Nhà_giả_kim_(sách).jpg',4),(10,NULL,NULL,_binary '',NULL,NULL,'product/pictures/camonnguoilon.jpg','camonnguoilon.jpg',5);

UNLOCK TABLES;


