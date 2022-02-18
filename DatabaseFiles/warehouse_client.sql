-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: warehouse
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idclient_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Myla Aiden','Parkfield Beeches nr.25','Myla@gmail.com'),(2,'Morton Wat','Portbury Lane n.12','WatWat@gmail.com'),(3,'Berny Fred','Drummond Woodlands n.22','BFred@yahoo.com'),(4,'Perce Travers','Sefton Walk n.2','PerceTra@gmail.com'),(5,'Florin Salam','Strada Banilor n.1','numaiunul@gmail.com'),(6,'Debi Lesly','Margaret Field n.5','DebiLesi@yahoo.com'),(7,'Elnora Thurstan','Chaucer Cottages n.76','Elnora@gmail.com'),(8,'Bryanna Lorene','Chaucer Cottages n.23','BryLor@yahoo.com'),(9,'Marisa Stacie','Wateville Road n.34','MariStacy@gmail.com'),(10,'Edie April','Crunch Croft n.51','Apriledi@gmail.com'),(11,'Raz','Dej n.5','adsasf@yahoo.com'),(12,'TestClient','Address n.23','test@gmail.com'),(13,'Test','Random','Random@gmail.com'),(14,'Andrei','Cluj n.24','Andrei@gmail.com'),(15,'Andrew','Dej n.2','andre@gmail.com'),(16,'Guta','Street street n.0','guta@gmail.com'),(28,'Valeriya Dervila','Wentworth Lane','VeleryDervi@gmail.com'),(29,'Luule Sarah','Olive Brae n.3','Luuuuuule@gmail.com');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-26 10:51:59
