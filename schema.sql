/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.7.2-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: shopping_cart_localization
-- ------------------------------------------------------
-- Server version	11.8.5-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cart_record_id` int(11) DEFAULT NULL,
  `item_number` int(11) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_record_id` (`cart_record_id`),
  CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_record_id`) REFERENCES `cart_records` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES
(1,2,1,65,2,130),
(2,2,2,43,3,129),
(3,2,3,2,4,8),
(4,2,4,3,1,3),
(8,3,1,34,1,34),
(9,3,2,2,3,6),
(11,4,1,11,2,22),
(12,4,2,23,3,69),
(13,4,3,43,4,172);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_records`
--

DROP TABLE IF EXISTS `cart_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_items` int(11) NOT NULL,
  `total_cost` double NOT NULL,
  `language` varchar(10) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_records`
--

LOCK TABLES `cart_records` WRITE;
/*!40000 ALTER TABLE `cart_records` DISABLE KEYS */;
INSERT INTO `cart_records` VALUES
(1,4,14,'English','2026-04-02 21:44:59'),
(2,10,270,'English','2026-04-02 22:07:08'),
(3,4,40,'English','2026-04-02 22:08:01'),
(4,9,263,'Japanese','2026-04-02 22:12:09');
/*!40000 ALTER TABLE `cart_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `localization_strings`
--

DROP TABLE IF EXISTS `localization_strings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `localization_strings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL,
  `value` varchar(255) NOT NULL,
  `language` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `localization_strings`
--

LOCK TABLES `localization_strings` WRITE;
/*!40000 ALTER TABLE `localization_strings` DISABLE KEYS */;
INSERT INTO `localization_strings` VALUES
(1,'ui.label.selectLanguage','Select the language:','English'),
(2,'ui.button.confirmLanguage','Confirm language','English'),
(3,'ui.label.enterNumbers','Enter number of items:','English'),
(4,'ui.button.enterItems','Enter items','English'),
(5,'ui.label.itemQuantity','Item quantity','English'),
(6,'ui.label.unitPrice','Unit price','English'),
(7,'ui.button.calculate','Calculate total','English'),
(8,'ui.label.total','Total:','English'),
(9,'ui.label.selectLanguage','اختر اللغة:','Arabic'),
(10,'ui.button.confirmLanguage','تأكيد اللغة','Arabic'),
(11,'ui.label.enterNumbers','أدخل عدد العناصر:','Arabic'),
(12,'ui.button.enterItems','إدخال العناصر','Arabic'),
(13,'ui.label.itemQuantity','الكمية','Arabic'),
(14,'ui.label.unitPrice','سعر الوحدة','Arabic'),
(15,'ui.button.calculate','احسب المجموع','Arabic'),
(16,'ui.label.total','المجموع:','Arabic'),
(17,'ui.label.selectLanguage','Valitse kieli:','Finnish'),
(18,'ui.button.confirmLanguage','Vahvista kieli','Finnish'),
(19,'ui.label.enterNumbers','Syötä nimikkeiden määrä:','Finnish'),
(20,'ui.button.enterItems','Lisää nimikkeet','Finnish'),
(21,'ui.label.itemQuantity','Määrä','Finnish'),
(22,'ui.label.unitPrice','Yksikköhinta','Finnish'),
(23,'ui.button.calculate','Laske yhteensä','Finnish'),
(24,'ui.label.total','Yhteensä:','Finnish'),
(25,'ui.label.selectLanguage','言語を選択してください：','Japanese'),
(26,'ui.button.confirmLanguage','言語を確認','Japanese'),
(27,'ui.label.enterNumbers','項目の数を入力してください：','Japanese'),
(28,'ui.button.enterItems','項目を入力','Japanese'),
(29,'ui.label.itemQuantity','数量','Japanese'),
(30,'ui.label.unitPrice','単価','Japanese'),
(31,'ui.button.calculate','合计を計算','Japanese'),
(32,'ui.label.total','合计：','Japanese'),
(33,'ui.label.selectLanguage','Välj språk:','Swedish'),
(34,'ui.button.confirmLanguage','Bekräfta språk','Swedish'),
(35,'ui.label.enterNumbers','Ange antal artiklar:','Swedish'),
(36,'ui.button.enterItems','Lägg till artiklar','Swedish'),
(37,'ui.label.itemQuantity','Antal','Swedish'),
(38,'ui.label.unitPrice','Enhetspris','Swedish'),
(39,'ui.button.calculate','Beräkna totalt','Swedish'),
(40,'ui.label.total','Totalt:','Swedish');
/*!40000 ALTER TABLE `localization_strings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'shopping_cart_localization'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2026-04-03 17:52:47
