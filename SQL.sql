-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: diplomremake
-- ------------------------------------------------------
-- Server version	5.7.21-log

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

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` bigint(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Amdin_User_FK` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (327,'predator.1199@mailru','795155832');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_work`
--

DROP TABLE IF EXISTS `course_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_work` (
  `id` bigint(20) NOT NULL,
  `place_practice` varchar(45) DEFAULT NULL,
  `full_name_boss` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Coursework_Reating_FK_idx` (`id`),
  CONSTRAINT `Coursework_Reating_FK` FOREIGN KEY (`id`) REFERENCES `marks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_work`
--

LOCK TABLES `course_work` WRITE;
/*!40000 ALTER TABLE `course_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diplom`
--

DROP TABLE IF EXISTS `diplom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diplom` (
  `id` bigint(20) NOT NULL,
  `thesis` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Diplom_Reating_FK_idx` (`id`),
  CONSTRAINT `Diplom_Reating_FK` FOREIGN KEY (`id`) REFERENCES `marks` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diplom`
--

LOCK TABLES `diplom` WRITE;
/*!40000 ALTER TABLE `diplom` DISABLE KEYS */;
/*!40000 ALTER TABLE `diplom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupstud`
--

DROP TABLE IF EXISTS `groupstud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupstud` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) DEFAULT NULL,
  `year` decimal(4,0) DEFAULT NULL,
  `speciality_id` bigint(20) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `group_spec_fk_idx` (`speciality_id`),
  CONSTRAINT `group_spec_fk` FOREIGN KEY (`speciality_id`) REFERENCES `speciality` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupstud`
--

LOCK TABLES `groupstud` WRITE;
/*!40000 ALTER TABLE `groupstud` DISABLE KEYS */;
INSERT INTO `groupstud` VALUES (32,'Testing',2017,24,'выпустились'),(33,'PR20146',2015,24,'выпустились'),(36,'ПР15-03',2015,24,'выпустились'),(49,'ПР15-04',2015,24,'обучаются'),(50,'ИП15-05',2015,24,'обучаются'),(51,'ВБ14-03',2014,38,'обучаются'),(52,'МК14-05',2024,37,'обучаются'),(53,'СИС14-04',2014,39,'обучаются'),(54,'ТП14-05',2014,39,'обучаются');
/*!40000 ALTER TABLE `groupstud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marks`
--

DROP TABLE IF EXISTS `marks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `student_id` bigint(20) NOT NULL,
  `statement_id` bigint(20) DEFAULT NULL,
  `mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Perfomance_Student_FK_idx` (`student_id`),
  KEY `Reating_Statement_FK_idx` (`statement_id`),
  CONSTRAINT `Reating_Statement_FK` FOREIGN KEY (`statement_id`) REFERENCES `statement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Reating_Student_FK` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marks`
--

LOCK TABLES `marks` WRITE;
/*!40000 ALTER TABLE `marks` DISABLE KEYS */;
INSERT INTO `marks` VALUES (141,432,46,3),(142,433,46,5),(143,434,46,5),(144,435,46,3),(145,436,46,5),(146,437,46,5),(147,438,46,4),(148,439,46,5),(149,440,46,5),(150,441,46,5),(151,442,46,5),(152,443,46,5),(153,444,46,5),(154,445,46,5),(155,446,46,5),(156,447,46,5),(157,448,46,5),(158,449,46,5),(159,450,46,5),(160,451,46,2),(161,452,46,5),(162,453,46,5),(163,454,46,5),(164,456,46,5),(165,457,46,5),(166,458,46,5),(167,459,46,5),(168,460,46,5),(169,461,46,5),(170,462,46,5),(171,463,46,5),(172,464,46,5),(173,466,46,4),(174,467,46,4),(175,468,46,3),(176,469,46,3),(177,470,46,3),(178,471,46,3),(179,472,46,3),(180,473,46,3),(181,474,46,3),(182,475,46,3),(183,476,46,NULL),(184,477,46,NULL),(185,478,46,NULL),(186,480,46,NULL),(187,481,46,NULL),(188,484,46,NULL),(189,485,46,NULL),(190,487,46,NULL),(191,488,46,NULL),(192,489,46,NULL),(193,490,46,NULL),(194,491,46,NULL),(195,492,46,NULL),(196,493,46,NULL),(197,494,46,NULL),(198,495,46,NULL),(199,496,46,NULL),(200,497,46,NULL),(201,498,46,NULL),(202,499,46,NULL),(203,500,46,NULL),(204,501,46,NULL),(205,502,46,NULL),(206,503,46,NULL),(207,504,46,NULL),(208,505,46,NULL),(209,506,46,NULL),(210,507,46,NULL),(211,508,46,NULL),(268,559,47,0),(269,560,47,1),(270,561,47,0),(271,562,47,1),(272,563,47,0),(273,564,47,0),(274,565,47,1),(275,566,47,0),(276,567,47,1),(277,568,47,0),(278,569,47,1),(283,577,48,4),(284,578,48,3),(285,579,48,3),(286,580,48,3),(287,581,48,5),(288,582,48,4),(289,583,48,5),(290,577,49,2),(291,578,49,5),(292,579,49,3),(293,580,49,4),(294,581,49,5),(295,582,49,4),(296,583,49,5),(297,577,50,1),(298,578,50,0),(299,579,50,0),(300,580,50,0),(301,581,50,1),(302,582,50,1),(303,583,50,0),(304,584,51,2),(305,585,51,4),(306,586,51,5),(307,587,51,4),(308,588,51,5),(309,589,51,5),(310,590,51,5);
/*!40000 ALTER TABLE `marks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speciality`
--

DROP TABLE IF EXISTS `speciality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speciality` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_speciality` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speciality`
--

LOCK TABLES `speciality` WRITE;
/*!40000 ALTER TABLE `speciality` DISABLE KEYS */;
INSERT INTO `speciality` VALUES (24,'Программирование в компьютерных системах'),(36,'Экономика'),(37,'Механик'),(38,'Веб-программирование'),(39,'Системный администратор');
/*!40000 ALTER TABLE `speciality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statement`
--

DROP TABLE IF EXISTS `statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `teacher_id` bigint(20) DEFAULT NULL,
  `hours` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `statement_group_fk_idx` (`group_id`),
  KEY `statement_subject_fk_idx` (`subject_id`),
  KEY `statement_teacher_fk_idx` (`teacher_id`),
  KEY `TypeCertification` (`type`),
  CONSTRAINT `statement_group_fk` FOREIGN KEY (`group_id`) REFERENCES `groupstud` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `statement_subject_fk` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `statement_teacher_fk` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statement`
--

LOCK TABLES `statement` WRITE;
/*!40000 ALTER TABLE `statement` DISABLE KEYS */;
INSERT INTO `statement` VALUES (46,50,2,'2019-06-11','Дифференцированный зачет',430,55),(47,53,7,'2019-06-12','Зачет',430,54),(48,51,5,'2019-06-13','Дифференцированный зачет',430,34),(49,51,8,'2019-06-13','Дифференцированный зачет',430,321),(50,51,4,'2019-06-13','Зачет',430,21),(51,52,7,'2019-06-13','Дифференцированный зачет',430,312);
/*!40000 ALTER TABLE `statement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `number_book` int(11) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Student_Group_FK_idx` (`group_id`),
  CONSTRAINT `Student_Group_FK` FOREIGN KEY (`group_id`) REFERENCES `groupstud` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `User_Student_FK` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (328,123423223,32),(387,123423223,32),(389,123423223,32),(390,123423223,32),(391,123423223,32),(392,123423223,32),(393,32131,32),(394,32131,32),(395,32131,32),(396,32131,32),(398,123423223,32),(399,123423223,32),(400,123423223,32),(401,123423223,32),(402,123423223,32),(403,123423223,32),(404,123423223,32),(405,123423223,32),(406,123423223,32),(407,123423223,32),(408,123423223,32),(409,123423223,32),(410,123423223,32),(413,123423223,32),(414,123423223,32),(415,123423223,32),(416,123423223,32),(417,123423223,32),(418,123423223,32),(419,123423223,32),(420,123423223,32),(421,123423223,32),(422,123423223,32),(423,123423223,32),(424,123423223,32),(425,123423223,32),(426,123423223,32),(427,123423223,32),(428,123423223,32),(429,123423223,32),(432,32131,50),(433,32131,50),(434,32131,50),(435,123423223,50),(436,123423223,50),(437,123423223,50),(438,123423223,50),(439,123423223,50),(440,123423223,50),(441,123423223,50),(442,32131,50),(443,32131,50),(444,32131,50),(445,32131,50),(446,123423223,50),(447,123423223,50),(448,123423223,50),(449,123423223,50),(450,123423223,50),(451,123423223,50),(452,123423223,50),(453,123423223,50),(454,123423223,50),(456,123423223,50),(457,123423223,50),(458,123423223,50),(459,123423223,50),(460,123423223,50),(461,123423223,50),(462,123423223,50),(463,123423223,50),(464,123423223,50),(466,123423223,50),(467,123423223,50),(468,123423223,50),(469,123423223,50),(470,123423223,50),(471,123423223,50),(472,123423223,50),(473,123423223,50),(474,123423223,50),(475,123423223,50),(476,123423223,50),(477,123423223,50),(478,123423223,50),(480,32131,50),(481,32131,50),(484,32131,50),(485,32131,50),(487,32131,50),(488,32131,50),(489,32131,50),(490,32131,50),(491,32131,50),(492,32131,50),(493,32131,50),(494,32131,50),(495,32131,50),(496,32131,50),(497,32131,50),(498,32131,50),(499,32131,50),(500,32131,50),(501,32131,50),(502,32131,50),(503,32131,50),(504,32131,50),(505,32131,50),(506,32131,50),(507,32131,50),(508,32131,50),(543,312312,49),(545,123423223,49),(546,123423223,49),(547,123423223,49),(548,123423223,49),(549,123423223,49),(550,123423223,49),(551,123423223,49),(552,32131,49),(553,32131,49),(554,32131,49),(555,32131,49),(559,123423223,53),(560,123423223,53),(561,123423223,53),(562,123423223,53),(563,123423223,53),(564,123423223,53),(565,123423223,53),(566,32131,53),(567,32131,53),(568,32131,53),(569,32131,53),(570,323232,50),(571,32321,50),(572,32131231,50),(573,31231231,50),(574,31232131,50),(575,31232131,50),(576,5453453,50),(577,323232,51),(578,32321,51),(579,32131231,51),(580,31231231,51),(581,31232131,51),(582,31232131,51),(583,5453453,51),(584,323232,52),(585,32321,52),(586,32131231,52),(587,31231231,52),(588,31232131,52),(589,31232131,52),(590,5453453,52),(591,323232,54),(592,32321,54),(593,32131231,54),(594,31231231,54),(595,31232131,54),(596,31232131,54),(597,5453453,54);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name_subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (2,'Программирование в компьютерных системах'),(3,'Русский язык'),(4,'Экономика'),(5,'Математика'),(6,'Базы данных'),(7,'Иформационная безопасность'),(8,'Операционные системы');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject_teacher_group`
--

DROP TABLE IF EXISTS `subject_teacher_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject_teacher_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL,
  `hours` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Subject_FK_idx` (`subject_id`),
  KEY `Teacher_FK_idx` (`teacher_id`),
  KEY `Group_FK_idx` (`group_id`),
  CONSTRAINT `Group_FK` FOREIGN KEY (`group_id`) REFERENCES `groupstud` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Subject_FK` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Teacher_FK` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject_teacher_group`
--

LOCK TABLES `subject_teacher_group` WRITE;
/*!40000 ALTER TABLE `subject_teacher_group` DISABLE KEYS */;
INSERT INTO `subject_teacher_group` VALUES (28,430,49,4,31),(31,430,50,2,35),(33,430,53,7,NULL),(34,557,53,6,NULL),(35,557,49,7,32),(36,430,51,5,120),(37,430,51,8,23),(38,430,51,4,25),(39,430,52,5,25),(40,430,52,8,126),(41,430,52,4,50),(45,430,52,7,155);
/*!40000 ALTER TABLE `subject_teacher_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL,
  `position` int(11) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKlicv62vmu1ydw117bbxqhkof1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (430,0,''),(516,0,''),(557,0,'');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `patronymic` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=598 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (327,'Игорь','Романов','Владимирович','admin','1234'),(328,'Игорь','dadadsa','Владимирович','student','1234'),(381,'Игорь','dadadsa','Владимирович','ИГDВ','84630'),(382,'Игорь','dadadsa','Владимирович','ИГDВ','42802'),(383,'Игорь','dadadsa','Владимирович','ИГDВ','74735'),(384,'Игорь','dadadsa','Владимирович','ИГDВ','25086'),(385,'Игорь','dadadsa','Владимирович','ИГDВ','77724'),(386,'Игорь','dadadsa','Владимирович','ИГDВ','58616'),(387,'Игорь','dadadsa','Владимирович','ИГDВ','38862'),(389,'Игорь','dadadsa','Владимирович','ИГDВ','58670'),(390,'Игорь','dadadsa','Владимирович','ИГDВ','24408'),(391,'Игорь','dadadsa','Владимирович','ИГDВ','54432'),(392,'Игорь','dadadsa','Владимирович','ИГDВ','60274'),(393,'Романов','Игорь','Владимирович','РОИВ','38174'),(394,'Романов','Игорь','Владимирович','РОИВ','31567'),(395,'Романов','Игорь','Владимирович','РОИВ','80746'),(396,'Романов','Игорь','Владимирович','РОИВ','82711'),(398,'Игорь','dadadsa','Владимирович','ИГDВ','44522'),(399,'Игорь','dadadsa','Владимирович','ИГDВ','37342'),(400,'Игорь','dadadsa','Владимирович','ИГDВ','27732'),(401,'Игорь','dadadsa','Владимирович','ИГDВ','06026'),(402,'Игорь','dadadsa','Владимирович','ИГDВ','86718'),(403,'Игорь','dadadsa','Владимирович','ИГDВ','74528'),(404,'Игорь','dadadsa','Владимирович','ИГDВ','05133'),(405,'Игорь','dadadsa','Владимирович','ИГDВ','50464'),(406,'Игорь','dadadsa','Владимирович','ИГDВ','24114'),(407,'Игорь','dadadsa','Владимирович','ИГDВ','63450'),(408,'Игорь','dadadsa','Владимирович','ИГDВ','81077'),(409,'Игорь','dadadsa','Владимирович','ИГDВ','84386'),(410,'Игорь','dadadsa','Владимирович','ИГDВ','25722'),(413,'Игорь','dadadsa','Владимирович','ИГDВ','04373'),(414,'Игорь','dadadsa','Владимирович','ИГDВ','45611'),(415,'Игорь','dadadsa','Владимирович','ИГDВ','66074'),(416,'Игорь','dadadsa','Владимирович','ИГDВ','71213'),(417,'Игорь','dadadsa','Владимирович','ИГDВ','86022'),(418,'Игорь','dadadsa','Владимирович','ИГDВ','28681'),(419,'Игорь','dadadsa','Владимирович','ИГDВ','17827'),(420,'Игорь','dadadsa','Владимирович','ИГDВ','36361'),(421,'Игорь','dadadsa','Владимирович','ИГDВ','30485'),(422,'Игорь','dadadsa','Владимирович','ИГDВ','27716'),(423,'Игорь','dadadsa','Владимирович','ИГDВ','47326'),(424,'Игорь','dadadsa','Владимирович','ИГDВ','21467'),(425,'Игорь','dadadsa','Владимирович','ИГDВ','82438'),(426,'Игорь','dadadsa','Владимирович','ИГDВ','58407'),(427,'Игорь','dadadsa','Владимирович','ИГDВ','73621'),(428,'Игорь','dadadsa','Владимирович','ИГDВ','56824'),(429,'Игорь','dadadsa','Владимирович','ИГDВ','36726'),(430,'Иван','Иванов','Иванович','Teacher','Teacher'),(431,'Романов','Игорь','Владимирович','РОИВ','48874'),(432,'Романов','Игорь','Владимирович','РОИВ','56338'),(433,'Романов','Игорь','Владимирович','РОИВ','12162'),(434,'Романов','Игорь','Владимирович','РОИВ','33325'),(435,'Игорь','dadadsa','Владимирович','ИГDВ','10144'),(436,'Игорь','dadadsa','Владимирович','ИГDВ','53547'),(437,'Игорь','dadadsa','Владимирович','ИГDВ','73834'),(438,'Игорь','dadadsa','Владимирович','ИГDВ','38273'),(439,'Игорь','dadadsa','Владимирович','ИГDВ','24868'),(440,'Игорь','dadadsa','Владимирович','ИГDВ','88268'),(441,'Игорь','dadadsa','Владимирович','ИГDВ','60207'),(442,'Романов','Игорь','Владимирович','РОИВ','74613'),(443,'Романов','Игорь','Владимирович','РОИВ','05446'),(444,'Романов','Игорь','Владимирович','РОИВ','27061'),(445,'Романов','Игорь','Владимирович','РОИВ','25537'),(446,'Игорь','dadadsa','Владимирович','ИГDВ','1023263'),(447,'Игорь','dadadsa','Владимирович','ИГDВ','7188658'),(448,'Игорь','dadadsa','Владимирович','ИГDВ','6246068'),(449,'Игорь','dadadsa','Владимирович','ИГDВ','8610951'),(450,'Игорь','dadadsa','Владимирович','ИГDВ','2490913'),(451,'Игорь','dadadsa','Владимирович','ИГDВ','3289606'),(452,'Игорь','dadadsa','Владимирович','ИГDВ','2845696'),(453,'Игорь','dadadsa','Владимирович','ИГDВ','0777504'),(454,'Игорь','dadadsa','Владимирович','ИГDВ','0372043'),(456,'Игорь','dadadsa','Владимирович','ИГDВ','8918881'),(457,'Игорь','dadadsa','Владимирович','ИГDВ','4047170'),(458,'Игорь','dadadsa','Владимирович','ИГDВ','3585820'),(459,'Игорь','dadadsa','Владимирович','ИГDВ','8391261'),(460,'Игорь','dadadsa','Владимирович','ИГDВ','5872582'),(461,'Игорь','dadadsa','Владимирович','ИГDВ','2480913'),(462,'Игорь','dadadsa','Владимирович','ИГDВ','6632016'),(463,'Игорь','dadadsa','Владимирович','ИГDВ','5766043'),(464,'Игорь','dadadsa','Владимирович','ИГDВ','2919684'),(466,'Игорь','dadadsa','Владимирович','ИГDВ','5737573'),(467,'Игорь','dadadsa','Владимирович','ИГDВ','1300498'),(468,'Игорь','dadadsa','Владимирович','ИГDВ','8311358'),(469,'Игорь','dadadsa','Владимирович','ИГDВ','3000062'),(470,'Игорь','dadadsa','Владимирович','ИГDВ','6072750'),(471,'Игорь','dadadsa','Владимирович','ИГDВ','6845317'),(472,'Игорь','dadadsa','Владимирович','ИГDВ','8726332'),(473,'Игорь','dadadsa','Владимирович','ИГDВ','2680930'),(474,'Игорь','dadadsa','Владимирович','ИГDВ','7127860'),(475,'Игорь','dadadsas','Владимирович','ИГDВ','0915406'),(476,'Игорь','dadadsa','Владимирович','ИГDВ','2191120'),(477,'Игорь','dadadsa','Владимирович','ИГDВ','0711045'),(478,'Игорь','dadadsa','Владимирович','ИГDВ','9385219'),(480,'Романов','Игорь','Владимирович','РОИВ','6125616'),(481,'Романов','Игорь','Владимирович','РОИВ','2454804'),(484,'Романов','Игорь','Владимирович','РОИВ','4239160'),(485,'Романов','Игорь','Владимирович','РОИВ','2274418'),(487,'Романов','Игорь','Владимирович','РОИВ','8912611'),(488,'Романов','Игорь','Владимирович','РОИВ','6564929'),(489,'Романов','Игорь','Владимирович','РОИВ','3085311'),(490,'Романов','Игорь','Владимирович','РОИВ','8623326'),(491,'Романов','Игорь','Владимирович','РОИВ','5812177'),(492,'Романов','Игорь','Владимирович','РОИВ','3752726'),(493,'Романов','Игорь','Владимирович','РОИВ','7659656'),(494,'Романов','Игорь','DS','РОИD','0541672'),(495,'Романов','Игорь','Владимирович','РОИВ','2739997'),(496,'Романов','Игорь','Владимирович','РОИВ','3637141'),(497,'Романов','Игорь','Владимирович','РОИВ','9873469'),(498,'Романов','Игорь','Владимирович','РОИВ','6570522'),(499,'Романов','Игорь','Владимирович','РОИВ','1865716'),(500,'Романов','Игорь','Владимирович','РОИВ','9208557'),(501,'Романов','Игорь','Владимирович','РОИВ','6352280'),(502,'Романов','Игорь','DS','РОИD','2464903'),(503,'Романов','Игорь','Владимирович','РОИВ','0611049'),(504,'Романов','Игорь','Владимирович','РОИВ','3341340'),(505,'Романов','Игорь','Владимирович','РОИВ','8274533'),(506,'Романов','Игорь','Владимирович','РОИВ','4774911'),(507,'Романов','Игорь','Владимирович','РОИВ','9188505'),(508,'Романов','Игорь','Владимирович','РОИВ','3501331'),(516,'Игорь','Романов','Владмимирович','OMIWW','7608439'),(543,'Романов ','Игорь','Владимирович','igor','1234'),(545,'Игорь','dadadsa','Владимирович','ИГDВ','5816605'),(546,'Игорь','dadadsa','Владимирович','ИГDВ','2301699'),(547,'Игорь','dadadsa','Владимирович','ИГDВ','8259134'),(548,'Игорь','dadadsa','Владимирович','ИГDВD','1201020'),(549,'Игорь','dadadsa','Владимирович','ИГDВ','3083328'),(550,'Игорь','dadadsa','Владимирович','ИГDВ','7730757'),(551,'Игорь','dadadsa','Владимирович','ИГDВ','7305689'),(552,'Романов','Игорь','Владимирович','РОИВ','5668874'),(553,'Романов','Игорь','Владимирович','РОИВ','7324873'),(554,'Романов','Игорь','Владимирович','РОИВ','3971170'),(555,'Романов','Игорь','Владимирович','РОИВ','9696614'),(557,'Сергей','Крылов','Сергеевич','Teacher2','Teacher2'),(559,'Игорь','dadadsa','Владимирович','ИГDВ','4196233'),(560,'Игорь','dadadsa','Владимирович','ИГDВ','8766825'),(561,'Игорь','dadadsa','Владимирович','ИГDВ','6623737'),(562,'Игорь','dadadsa','Владимирович','ИГDВ','3074466'),(563,'Игорь','dadadsa','Владимирович','ИГDВ','0085953'),(564,'Игорь','dadadsa','Владимирович','ИГDВ','1598980'),(565,'Игорь','dadadsa','Владимирович','ИГDВ','0176599'),(566,'Романов','Игорь','Владимирович','РОИВ','4885927'),(567,'Романов','Игорь','Владимирович','РОИВ','4073921'),(568,'Романов','Игорь','Владимирович','РОИВ','8553147'),(569,'Романов','Игорь','Владимирович','РОИВ','1588704'),(570,'Игорь','Романов','Владимирович','FFDDSDS','31312312'),(571,'Игорь','Романов','Владимирович','DSDSD','32131231'),(572,'Игорь','Романов','Владимирович','DDDD','31231'),(573,'Игорь','Романов','Владимирович','SSSS','31231'),(574,'Игорь','Романов','Владимирович','SSSSS','3123'),(575,'Игорь','Романов','Владимирович','SSSSSfds','31312'),(576,'Игорь','Романов','Владимирович','dadasdas','313123'),(577,'Игорь','Романов','Владимирович','ИГРВ','1721563'),(578,'Игорь','Романов','Владимирович','ИГРВ','8777079'),(579,'Игорь','Романов','Владимирович','ИГРВ','2754404'),(580,'Игорь','Романов','Владимирович','ИГРВ','8829817'),(581,'Игорь','Романов','Владимирович','ИГРВ','0636594'),(582,'Игорь','Романов','Владимирович','ИГРВ','3631891'),(583,'Игорь','Романов','Владимирович','ИГРВ','7520262'),(584,'Игорь','Романов','Владимирович','ИГРВ','1934436'),(585,'Игорь','Романов','Владимирович','ИГРВ','9755948'),(586,'Игорь','Романов','Владимирович','ИГРВ','8758198'),(587,'Игорь','Романов','Владимирович','ИГРВ','5784999'),(588,'Игорь','Романов','Владимирович','ИГРВ','8256797'),(589,'Игорь','Романов','Владимирович','ИГРВ','5746025'),(590,'Игорь','Романов','Владимирович','ИГРВ','8464076'),(591,'Игорь','Романов','Владимирович','ИГРВ','0239995'),(592,'Игорь','Романов','Владимирович','ИГРВ','6057847'),(593,'Игорь','Романов','Владимирович','ИГРВ','6469499'),(594,'Игорь','Романов','Владимирович','ИГРВ','3250897'),(595,'Игорь','Романов','Владимирович','ИГРВ','4025299'),(596,'Игорь','Романов','Владимирович','ИГРВ','0052432'),(597,'Игорь','Романов','Владимирович','ИГРВ','8876652');
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

-- Dump completed on 2019-06-13  9:28:47
