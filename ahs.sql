-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 19, 2022 at 10:12 AM
-- Server version: 8.0.27
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ahs`
--

-- --------------------------------------------------------

--
-- Table structure for table `consumers`
--

DROP TABLE IF EXISTS `consumers`;
CREATE TABLE IF NOT EXISTS `consumers` (
  `CID` int NOT NULL AUTO_INCREMENT,
  `Uid` int DEFAULT NULL,
  `CName` varchar(20) NOT NULL,
  `Wallet` double NOT NULL,
  `Amnt_bought` double DEFAULT NULL,
  PRIMARY KEY (`CID`),
  KEY `Uid` (`Uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `consumers`
--

INSERT INTO `consumers` (`CID`, `Uid`, `CName`, `Wallet`, `Amnt_bought`) VALUES
(1, 2, 'Glo	', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `importers`
--

DROP TABLE IF EXISTS `importers`;
CREATE TABLE IF NOT EXISTS `importers` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Uid` int DEFAULT NULL,
  `IName` varchar(100) DEFAULT NULL,
  `Max_refil` int DEFAULT NULL,
  `Wallet` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `Price` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Uid` (`Uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `importers`
--

INSERT INTO `importers` (`ID`, `Uid`, `IName`, `Max_refil`, `Wallet`, `Price`) VALUES
(1, 4, 'KPA', 1000, '500.0', '1'),
(2, 7, 'ones imp', 1000, '1000', '1.0'),
(3, 4, 'Glo Imprt', 1000, '1000', '2.0');

-- --------------------------------------------------------

--
-- Table structure for table `localdistributors`
--

DROP TABLE IF EXISTS `localdistributors`;
CREATE TABLE IF NOT EXISTS `localdistributors` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Uid` int DEFAULT NULL,
  `LDName` varchar(100) DEFAULT NULL,
  `Stock_level` int DEFAULT '0',
  `Price` double DEFAULT NULL,
  `Max_refil` int DEFAULT NULL,
  `Wallet` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `Uid` (`Uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `localdistributors`
--

INSERT INTO `localdistributors` (`ID`, `Uid`, `LDName`, `Stock_level`, `Price`, `Max_refil`, `Wallet`) VALUES
(1, 3, 'Riva', 0, 2, 100, '0'),
(2, 6, 'ones dist', 0, 2, 100, '0'),
(3, 3, 'Glo Dist', 0, 3, 100, '0');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Uid` varchar(100) DEFAULT NULL,
  `Pass` varchar(100) DEFAULT NULL,
  `Login_status` int DEFAULT '1',
  `user_type` int NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Uid`, `Pass`, `Login_status`, `user_type`) VALUES
(1, 'a1', '123', 0, 4),
(2, 'c38', '123', 0, 1),
(3, 'l74', '123', 0, 2),
(4, 'i29', '123', 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE IF NOT EXISTS `warehouse` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Imp_id` int DEFAULT NULL,
  `Stock_level` int DEFAULT '0',
  `Price` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Imp_id` (`Imp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `warehouse`
--

INSERT INTO `warehouse` (`ID`, `Imp_id`, `Stock_level`, `Price`) VALUES
(1, 3, 0, '1');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `consumers`
--
ALTER TABLE `consumers`
  ADD CONSTRAINT `consumers_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `users` (`ID`);

--
-- Constraints for table `importers`
--
ALTER TABLE `importers`
  ADD CONSTRAINT `importers_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `users` (`ID`);

--
-- Constraints for table `localdistributors`
--
ALTER TABLE `localdistributors`
  ADD CONSTRAINT `localdistributors_ibfk_1` FOREIGN KEY (`Uid`) REFERENCES `users` (`ID`);

--
-- Constraints for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`Imp_id`) REFERENCES `importers` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
