-- MySQL dump 10.13  Distrib 5.7.23, for Win64 (x86_64)
--
-- Host: localhost    Database: cruise_company
-- ------------------------------------------------------
-- Server version	5.7.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE SCHEMA IF NOT EXISTS cruise_company;
USE cruise_company;

--
-- Table structure for table `bonuses`
--

DROP TABLE IF EXISTS `bonuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bonuses` (
  `bo_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`bo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bonuses`
--

LOCK TABLES `bonuses` WRITE;
/*!40000 ALTER TABLE `bonuses` DISABLE KEYS */;
INSERT INTO `bonuses` VALUES (1,'swimming_pool'),(2,'beauty_salon'),(3,'gym'),(4,'fitness_club'),(5,'cinema'),(6,'swiss_table');
/*!40000 ALTER TABLE `bonuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `co_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`co_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'bel'),(2,'bgr'),(3,'bhs'),(4,'cub'),(5,'cyp'),(6,'deu'),(7,'dnk'),(8,'egy'),(9,'esp'),(10,'fin'),(11,'fra'),(12,'gbr'),(13,'grc'),(14,'hti'),(15,'irl'),(16,'isr'),(17,'ita'),(18,'lbn'),(19,'mco'),(20,'mdv'),(21,'mlt'),(22,'nld'),(23,'nor'),(24,'prt'),(25,'rou'),(26,'swe'),(27,'tur'),(28,'ukr');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cruise`
--

DROP TABLE IF EXISTS `cruise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cruise` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `ship_sh_id` int(11) NOT NULL,
  PRIMARY KEY (`cr_id`),
  KEY `fk_cruise_ship1` (`ship_sh_id`),
  CONSTRAINT `fk_cruise_ship1` FOREIGN KEY (`ship_sh_id`) REFERENCES `ship` (`sh_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cruise`
--

LOCK TABLES `cruise` WRITE;
/*!40000 ALTER TABLE `cruise` DISABLE KEYS */;
INSERT INTO `cruise` VALUES (1,'Exotic Midterrean',320,'2019-08-12 00:00:00',3),(2,'Warm Cuba',550,'2019-06-11 00:00:00',4);
/*!40000 ALTER TABLE `cruise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `excursion`
--

DROP TABLE IF EXISTS `excursion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `excursion` (
  `exc_id` int(11) NOT NULL AUTO_INCREMENT,
  `info` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `harbor_hb_id` int(11) NOT NULL,
  PRIMARY KEY (`exc_id`),
  KEY `fx_excursion_harbor1` (`harbor_hb_id`),
  CONSTRAINT `fx_excursion_harbor1` FOREIGN KEY (`harbor_hb_id`) REFERENCES `harbor` (`hb_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `excursion`
--

LOCK TABLES `excursion` WRITE;
/*!40000 ALTER TABLE `excursion` DISABLE KEYS */;
INSERT INTO `excursion` VALUES (1,'tour.info.alexandria',300,133),(2,'tour.info.barcelona_1',350,118),(3,'tour.info.odesa',150,89),(4,'tour.info.telaviv',240,129),(5,'tour.info.marseille_1',230,126),(6,'tour.info.marseille_2',180,126),(7,'tour.info.barcelona_2',300,118);
/*!40000 ALTER TABLE `excursion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `harbor`
--

DROP TABLE IF EXISTS `harbor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `harbor` (
  `hb_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `country_co_id` int(11) NOT NULL,
  PRIMARY KEY (`hb_id`),
  KEY `fk_harbor_country1_idx` (`country_co_id`),
  CONSTRAINT `fk_harbor_country1` FOREIGN KEY (`country_co_id`) REFERENCES `country` (`co_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `harbor`
--

LOCK TABLES `harbor` WRITE;
/*!40000 ALTER TABLE `harbor` DISABLE KEYS */;
INSERT INTO `harbor` VALUES (76,'portauprince',14),(77,'emden',6),(78,'valencia',9),(79,'palermo',17),(80,'beirut',18),(81,'galway',15),(82,'burgas',2),(83,'kalamata',13),(84,'edinburgh',12),(85,'venice',17),(86,'larnaca',5),(87,'nice',11),(88,'rostock',6),(89,'odesa',28),(90,'izmir',27),(91,'athens',13),(92,'bristol',12),(93,'helsinki',10),(94,'liverpool',12),(95,'catania',17),(96,'flensburg',6),(97,'cannes',11),(98,'malmo',26),(99,'naples',17),(100,'malaga',9),(101,'antalia',27),(102,'santiagodecuba',4),(103,'rotterdam',22),(104,'newport',12),(105,'kiel',6),(106,'porto',24),(107,'dunkirk',11),(108,'sunnybeach',2),(109,'male',20),(110,'portsmouth',12),(111,'turku',10),(112,'netanya',16),(113,'oslo',23),(114,'ostend',1),(115,'bergen',23),(116,'haifa',16),(117,'belfast',12),(118,'barcelona',9),(119,'varna',2),(120,'aberdeen',12),(121,'hamburg',6),(122,'stockholm',26),(123,'constanta',25),(124,'gothenburg',26),(125,'mersin',27),(126,'marseille',11),(127,'havana',4),(128,'calais',11),(129,'telaviv',16),(130,'patras',13),(131,'dublin',15),(132,'istanbul',27),(133,'alexandria',8),(134,'copenhagen',7),(135,'nassau',3),(136,'mykolaiv',28),(137,'valetta',21),(138,'oulu',10),(139,'antwerp',1),(140,'mangalia',25),(141,'lisbon',24),(142,'samsun',27),(143,'thessaloniki',13),(144,'portsaid',8),(145,'amsterdam',22),(146,'cardiff',12),(147,'ordu',27),(148,'wilhelmshaven',6),(149,'genoa',17),(150,'monaco',19);
/*!40000 ALTER TABLE `harbor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `ro_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_type_rt_id` int(11) NOT NULL,
  `ship_sh_id` int(11) NOT NULL,
  PRIMARY KEY (`ro_id`),
  KEY `fk_room_room_type1` (`room_type_rt_id`),
  KEY `fk_room_ship1` (`ship_sh_id`),
  CONSTRAINT `fk_room_room_type1` FOREIGN KEY (`room_type_rt_id`) REFERENCES `room_type` (`rt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_ship1` FOREIGN KEY (`ship_sh_id`) REFERENCES `ship` (`sh_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,1,1),(2,1,1),(3,1,1),(4,1,1),(5,1,1),(6,1,1),(7,1,1),(8,1,1),(9,1,1),(10,2,1),(11,2,1),(12,2,1),(13,2,1),(14,2,1),(15,2,1),(16,3,1),(17,3,1),(18,3,1),(19,3,1),(20,4,1),(21,4,1),(22,4,1),(23,1,2),(24,1,2),(25,1,2),(26,1,2),(27,1,2),(28,1,2),(29,1,2),(30,1,2),(31,1,2),(32,1,2),(33,1,2),(34,1,2),(35,2,2),(36,2,2),(37,2,2),(38,2,2),(39,2,2),(40,2,2),(41,2,2),(42,3,2),(43,3,2),(44,3,2),(45,3,2),(46,3,2),(47,3,2),(48,4,2),(49,4,2),(50,1,3),(51,1,3),(52,1,3),(53,1,3),(54,1,3),(55,1,3),(56,1,3),(57,1,3),(58,1,3),(59,1,3),(60,1,3),(61,1,3),(62,1,3),(63,1,3),(64,1,3),(65,1,3),(66,2,3),(67,2,3),(68,2,3),(69,2,3),(70,2,3),(71,2,3),(72,2,3),(73,2,3),(74,2,3),(75,2,3),(76,2,3),(77,2,3),(78,3,3),(79,3,3),(80,3,3),(81,3,3),(82,3,3),(83,3,3),(84,4,3),(85,4,3),(86,4,3),(87,1,4),(88,1,4),(89,1,4),(90,1,4),(91,1,4),(92,1,4),(93,1,4),(94,1,4),(95,1,4),(96,1,4),(97,2,4),(98,2,4),(99,2,4),(100,2,4),(101,2,4),(102,2,4),(103,3,4),(104,3,4),(105,3,4),(106,3,4),(107,4,4),(108,4,4),(109,1,5),(110,1,5),(111,1,5),(112,1,5),(113,1,5),(114,1,5),(115,1,5),(116,1,5),(117,1,5),(118,1,5),(119,1,5),(120,1,5),(121,1,5),(122,1,5),(123,1,5),(124,1,5),(125,1,5),(126,1,5),(127,2,5),(128,2,5),(129,2,5),(130,2,5),(131,2,5),(132,2,5),(133,2,5),(134,2,5),(135,2,5),(136,2,5),(137,2,5),(138,2,5),(139,2,5),(140,3,5),(141,3,5),(142,3,5),(143,3,5),(144,3,5),(145,3,5),(146,4,5),(147,4,5),(148,4,5),(149,4,5),(150,4,5);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_bonuses`
--

DROP TABLE IF EXISTS `room_bonuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_bonuses` (
  `room_type_rt_id` int(11) NOT NULL,
  `bonuses_bo_id` int(11) NOT NULL,
  PRIMARY KEY (`room_type_rt_id`,`bonuses_bo_id`),
  KEY `fk_room_bonuses_room_type1_idx` (`room_type_rt_id`),
  KEY `fk_room_bonuses_bonuses1` (`bonuses_bo_id`),
  CONSTRAINT `fk_room_bonuses_bonuses1` FOREIGN KEY (`bonuses_bo_id`) REFERENCES `bonuses` (`bo_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_bonuses_room_type1` FOREIGN KEY (`room_type_rt_id`) REFERENCES `room_type` (`rt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_bonuses`
--

LOCK TABLES `room_bonuses` WRITE;
/*!40000 ALTER TABLE `room_bonuses` DISABLE KEYS */;
INSERT INTO `room_bonuses` VALUES (1,5),(2,1),(2,2),(2,3),(2,5),(3,1),(3,2),(3,3),(3,4),(3,5),(4,1),(4,2),(4,3),(4,4),(4,5),(4,6);
/*!40000 ALTER TABLE `room_bonuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_type` (
  `rt_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cost_modifier` int(11) NOT NULL,
  PRIMARY KEY (`rt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_type`
--

LOCK TABLES `room_type` WRITE;
/*!40000 ALTER TABLE `room_type` DISABLE KEYS */;
INSERT INTO `room_type` VALUES (1,'econom',1),(2,'lux',2),(3,'business',3),(4,'presidental',4);
/*!40000 ALTER TABLE `room_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `harbor_hb_id` int(11) NOT NULL,
  `cruise_cr_id` int(11) NOT NULL,
  `arrival` datetime NOT NULL,
  `departure` datetime NOT NULL,
  PRIMARY KEY (`harbor_hb_id`,`cruise_cr_id`),
  KEY `fk_cruise_cruise1_idx` (`cruise_cr_id`),
  CONSTRAINT `fk_cruise_cruise1` FOREIGN KEY (`cruise_cr_id`) REFERENCES `cruise` (`cr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cruise_harbor1` FOREIGN KEY (`harbor_hb_id`) REFERENCES `harbor` (`hb_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (76,2,'2019-06-24 08:52:00','2019-06-24 08:52:00'),(78,1,'2019-08-21 12:10:00','2019-08-21 12:10:00'),(78,2,'2019-06-11 11:30:00','2019-06-11 11:30:00'),(79,1,'2019-08-17 02:10:00','2019-08-17 12:40:00'),(80,1,'2019-08-14 16:10:00','2019-08-14 19:55:00'),(86,1,'2019-08-14 09:45:00','2019-08-14 13:30:00'),(89,1,'2019-08-12 14:45:00','2019-08-12 14:45:00'),(91,1,'2019-08-13 22:00:00','2019-08-14 02:10:00'),(95,1,'2019-08-16 15:55:00','2019-08-16 22:30:00'),(99,1,'2019-08-17 15:35:00','2019-08-18 00:10:00'),(100,2,'2019-06-11 19:25:00','2019-06-12 12:40:00'),(118,1,'2019-08-20 05:40:00','2019-08-21 06:20:00'),(126,1,'2019-08-19 04:20:00','2019-08-19 23:55:00'),(127,2,'2019-06-18 16:50:00','2019-06-19 14:50:00'),(129,1,'2019-08-14 23:15:00','2019-08-15 08:20:00'),(132,1,'2019-08-13 05:40:00','2019-08-13 14:25:00'),(133,1,'2019-08-15 15:40:00','2019-08-16 06:40:00'),(135,2,'2019-06-17 07:00:00','2019-06-18 06:50:00'),(141,2,'2019-06-12 23:55:00','2019-06-13 15:20:00'),(149,1,'2019-08-18 07:20:00','2019-08-18 22:25:00');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ship`
--

DROP TABLE IF EXISTS `ship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ship` (
  `sh_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `crew_number` int(11) NOT NULL,
  PRIMARY KEY (`sh_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ship`
--

LOCK TABLES `ship` WRITE;
/*!40000 ALTER TABLE `ship` DISABLE KEYS */;
INSERT INTO `ship` VALUES (1,'Admiral Nakhimov',350),(2,'Indomitable',572),(3,'Ocean',780),(4,'Dnipro',230),(5,'Queen Mary',550);
/*!40000 ALTER TABLE `ship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ti_id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_date` datetime NOT NULL,
  `price` int(11) NOT NULL,
  `user_login` varchar(40) NOT NULL,
  `room_ro_id` int(11) NOT NULL,
  `cruise_cr_id` int(11) NOT NULL,
  PRIMARY KEY (`ti_id`),
  KEY `fk_ticket_room1` (`room_ro_id`),
  KEY `fk_ticket_user` (`user_login`),
  KEY `fk_ticket_cruise_idx` (`cruise_cr_id`),
  CONSTRAINT `fk_ticket_cruise` FOREIGN KEY (`cruise_cr_id`) REFERENCES `cruise` (`cr_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_room1` FOREIGN KEY (`room_ro_id`) REFERENCES `room` (`ro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`user_login`) REFERENCES `user` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (2,'2019-03-03 00:00:00',320,'user1111',51,1),(4,'2019-03-22 00:25:21',320,'user1111',53,1),(5,'2019-03-22 18:35:49',320,'user1111',52,1),(6,'2019-03-24 16:30:02',1280,'user1111',84,1);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_excursion`
--

DROP TABLE IF EXISTS `ticket_excursion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_excursion` (
  `ticket_ti_id` int(11) NOT NULL,
  `excursion_exc_id` int(11) NOT NULL,
  KEY `ticket_excursion_ticket1_idx` (`ticket_ti_id`),
  KEY `ticket_excursion_excursion1_idx` (`excursion_exc_id`),
  CONSTRAINT `ticket_excursion_excursion1` FOREIGN KEY (`excursion_exc_id`) REFERENCES `excursion` (`exc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ticket_excursion_ticket1` FOREIGN KEY (`ticket_ti_id`) REFERENCES `ticket` (`ti_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_excursion`
--

LOCK TABLES `ticket_excursion` WRITE;
/*!40000 ALTER TABLE `ticket_excursion` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_excursion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `login` varchar(40) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `admin` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`login`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','admin','cruise_company@gmail.com','Admin','Admin',1),('moreman','moreman','said122@gmail.com','Said','Saidov',0),('user1111','user1111','denis.ua22@gmail.com','Den','Dean4iq',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-27 11:07:20
