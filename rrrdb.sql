-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 30, 2025 at 08:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rrrdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `ordersupplier`
--

CREATE TABLE `ordersupplier` (
  `OrderID` int(7) NOT NULL,
  `SProductID` varchar(5) NOT NULL,
  `OrderDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `PAmounts` int(10) NOT NULL DEFAULT 0,
  `TotalPrice` decimal(7,2) NOT NULL DEFAULT 0.00,
  `ID` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `productmanagements`
--

CREATE TABLE `productmanagements` (
  `ProductID` int(5) NOT NULL,
  `ProductName` varchar(30) NOT NULL,
  `Price` decimal(7,2) NOT NULL,
  `ProductTypeID` varchar(20) NOT NULL,
  `Amounts` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `productmanagements`
--

INSERT INTO `productmanagements` (`ProductID`, `ProductName`, `Price`, `ProductTypeID`, `Amounts`) VALUES
(4, 'Avocado', 36.00, 'T001', 90),
(10, 'Haribo', 20.00, 'T002', 100),
(11, 'Tawan', 20.00, 'T002', 20),
(14, 'Grape', 50.00, 'T001', 203),
(15, 'Jollybear', 20.00, 'T002', 10),
(16, 'Yupi Jelly', 20.00, 'T002', 11);

-- --------------------------------------------------------

--
-- Table structure for table `producttypemanagements`
--

CREATE TABLE `producttypemanagements` (
  `ProductTypeID` varchar(4) NOT NULL,
  `ProductTypeName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `producttypemanagements`
--

INSERT INTO `producttypemanagements` (`ProductTypeID`, `ProductTypeName`) VALUES
('T001', 'Fruits'),
('T002', 'Snacks'),
('T003', 'Drinks'),
('T004', 'Medicine'),
('T005', 'Vegetable'),
('T006', 'Bakery'),
('T007', 'Ice-cream');

-- --------------------------------------------------------

--
-- Table structure for table `receiveproduct`
--

CREATE TABLE `receiveproduct` (
  `ReceiveID` int(7) NOT NULL,
  `OrderID` int(5) NOT NULL,
  `ReceiveDate` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receiveproduct`
--

INSERT INTO `receiveproduct` (`ReceiveID`, `OrderID`, `ReceiveDate`) VALUES
(7, 7, '2025-03-01'),
(8, 8, '2025-03-04'),
(9, 9, '2025-03-08'),
(10, 10, '2025-03-16'),
(17, 37, '2025-03-31'),
(18, 39, '2025-03-31'),
(19, 38, '2025-03-31'),
(20, 40, '2025-03-31'),
(21, 42, '2025-03-31');

-- --------------------------------------------------------

--
-- Table structure for table `returnproduct`
--

CREATE TABLE `returnproduct` (
  `ReturnID` int(7) NOT NULL,
  `OrderID` int(5) NOT NULL,
  `ReturnDate` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `returnproduct`
--

INSERT INTO `returnproduct` (`ReturnID`, `OrderID`, `ReturnDate`) VALUES
(1, 1, '2025-03-01'),
(2, 2, '2025-03-01'),
(3, 3, '2025-03-01'),
(4, 4, '2025-03-01'),
(5, 5, '2025-03-08'),
(6, 6, '2025-03-29'),
(12, 41, '2025-03-31'),
(13, 43, '2025-03-31');

-- --------------------------------------------------------

--
-- Table structure for table `suppliermanagements`
--

CREATE TABLE `suppliermanagements` (
  `ID` int(3) NOT NULL,
  `name` varchar(30) NOT NULL,
  `Contact_Person` varchar(50) NOT NULL,
  `Phone` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `suppliermanagements`
--

INSERT INTO `suppliermanagements` (`ID`, `name`, `Contact_Person`, `Phone`) VALUES
(1, 'Sermthai', 'Teepakorn Thipphamon', '0629970451'),
(2, 'MSU', 'Nattakan Tartong', '0636395409'),
(3, 'Thatphanom', 'Rongrit Tonlampu', '0612345678'),
(4, 'Bangkok', 'Paravee Thipphamon', '0653216654'),
(5, 'Kalasin', 'Nattakan Jantepa', '0642356543');

-- --------------------------------------------------------

--
-- Table structure for table `supplierproductmanagements`
--

CREATE TABLE `supplierproductmanagements` (
  `SProductID` varchar(5) NOT NULL,
  `SProductName` varchar(30) NOT NULL,
  `ProductTypeID` varchar(20) NOT NULL,
  `Price` decimal(7,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplierproductmanagements`
--

INSERT INTO `supplierproductmanagements` (`SProductID`, `SProductName`, `ProductTypeID`, `Price`) VALUES
('S0001', 'Apple', 'T001', 10.00),
('S0002', 'MayongChid', 'T001', 20.00),
('S0003', 'Yupi Jelly', 'T002', 20.00),
('S0004', 'Tawan', 'T002', 20.00),
('S0005', 'Grape', 'T001', 60.00),
('S0006', 'Melon', 'T001', 60.00),
('S0007', 'Doritos', 'T002', 20.00),
('S0008', 'BengBeng', 'T002', 10.00),
('S0009', 'Banana', 'T001', 20.00),
('S0010', 'Cornne', 'T002', 30.00),
('S0011', 'Pipo Gummy', 'T002', 30.00),
('S0012', 'Lotus Chicken Leg', 'T002', 15.00),
('S0013', 'Coconut', 'T001', 15.00),
('S0014', 'Avocado', 'T001', 36.00),
('S0015', 'Jollybear', 'T002', 20.00),
('S0016', 'Haribo', 'T002', 20.00),
('S0017', 'Yuro', 'T002', 20.00),
('S0018', 'YenYen', 'T003', 20.00),
('S0019', 'Ichitan', 'T003', 25.00),
('S0020', 'FarmHouse', 'T006', 12.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ordersupplier`
--
ALTER TABLE `ordersupplier`
  ADD PRIMARY KEY (`OrderID`),
  ADD KEY `SProductID` (`SProductID`),
  ADD KEY `ID` (`ID`);

--
-- Indexes for table `productmanagements`
--
ALTER TABLE `productmanagements`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `ProductTypeName` (`ProductTypeID`);

--
-- Indexes for table `producttypemanagements`
--
ALTER TABLE `producttypemanagements`
  ADD PRIMARY KEY (`ProductTypeID`);

--
-- Indexes for table `receiveproduct`
--
ALTER TABLE `receiveproduct`
  ADD PRIMARY KEY (`ReceiveID`),
  ADD KEY `OrderID` (`OrderID`),
  ADD KEY `OrderID_2` (`OrderID`);

--
-- Indexes for table `returnproduct`
--
ALTER TABLE `returnproduct`
  ADD PRIMARY KEY (`ReturnID`),
  ADD KEY `OrderID` (`OrderID`);

--
-- Indexes for table `suppliermanagements`
--
ALTER TABLE `suppliermanagements`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `supplierproductmanagements`
--
ALTER TABLE `supplierproductmanagements`
  ADD PRIMARY KEY (`SProductID`),
  ADD KEY `ProductTypeID` (`ProductTypeID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ordersupplier`
--
ALTER TABLE `ordersupplier`
  MODIFY `OrderID` int(7) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `productmanagements`
--
ALTER TABLE `productmanagements`
  MODIFY `ProductID` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `receiveproduct`
--
ALTER TABLE `receiveproduct`
  MODIFY `ReceiveID` int(7) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `returnproduct`
--
ALTER TABLE `returnproduct`
  MODIFY `ReturnID` int(7) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ordersupplier`
--
ALTER TABLE `ordersupplier`
  ADD CONSTRAINT `ordersupplier_ibfk_1` FOREIGN KEY (`SProductID`) REFERENCES `supplierproductmanagements` (`SProductID`),
  ADD CONSTRAINT `ordersupplier_ibfk_2` FOREIGN KEY (`ID`) REFERENCES `suppliermanagements` (`ID`);

--
-- Constraints for table `productmanagements`
--
ALTER TABLE `productmanagements`
  ADD CONSTRAINT `productmanagements_ibfk_1` FOREIGN KEY (`ProductTypeID`) REFERENCES `producttypemanagements` (`ProductTypeID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `supplierproductmanagements`
--
ALTER TABLE `supplierproductmanagements`
  ADD CONSTRAINT `supplierproductmanagements_ibfk_1` FOREIGN KEY (`ProductTypeID`) REFERENCES `producttypemanagements` (`ProductTypeID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
