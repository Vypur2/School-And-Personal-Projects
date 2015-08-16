-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 10, 2015 at 07:48 AM
-- Server version: 5.5.44-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ComputerHardwareDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `AccountUser`
--

CREATE TABLE IF NOT EXISTS `AccountUser` (
  `Uid` int(5) NOT NULL,
  `UName` varchar(12) NOT NULL,
  PRIMARY KEY (`UName`),
  UNIQUE KEY `UName` (`UName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `AccountUser`
--

INSERT INTO `AccountUser` (`Uid`, `UName`) VALUES
(2001, 'MechaMan12'),
(3001, 'RoboLover11'),
(1001, 'Techlover15');

-- --------------------------------------------------------

--
-- Stand-in structure for view `Account User View`
--
CREATE TABLE IF NOT EXISTS `Account User View` (
`PName` varchar(30)
,`Description` text
,`Price` float(6,2) unsigned
,`Rating` int(1) unsigned
,`DescriptionReview` text
);
-- --------------------------------------------------------

--
-- Table structure for table `Buys`
--

CREATE TABLE IF NOT EXISTS `Buys` (
  `Pid` int(5) NOT NULL,
  `UName` varchar(12) NOT NULL,
  `BuysID` int(5) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Pid`,`UName`,`BuysID`),
  UNIQUE KEY `BuysID` (`BuysID`),
  KEY `UName` (`UName`),
  KEY `Pid` (`Pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Buys`
--

INSERT INTO `Buys` (`Pid`, `UName`, `BuysID`, `Date`) VALUES
(1337, 'MechaMan12', 1, '2015-08-09'),
(1337, 'RoboLover11', 2, '2015-08-09'),
(4355, 'Techlover15', 3, '2015-08-01'),
(6753, 'MechaMan12', 4, '2015-08-20');

-- --------------------------------------------------------

--
-- Table structure for table `Company`
--

CREATE TABLE IF NOT EXISTS `Company` (
  `Cid` int(5) NOT NULL,
  `CName` varchar(20) NOT NULL,
  PRIMARY KEY (`Cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Company`
--

INSERT INTO `Company` (`Cid`, `CName`) VALUES
(15210, 'Kingston'),
(15213, 'Intel'),
(15217, 'Asus');

-- --------------------------------------------------------

--
-- Stand-in structure for view `Company User View`
--
CREATE TABLE IF NOT EXISTS `Company User View` (
`CName` varchar(20)
,`SName` varchar(20)
,`PName` varchar(30)
);
-- --------------------------------------------------------

--
-- Table structure for table `DistributesTo`
--

CREATE TABLE IF NOT EXISTS `DistributesTo` (
  `Cid` int(5) NOT NULL,
  `Sid` int(5) NOT NULL,
  PRIMARY KEY (`Cid`,`Sid`),
  KEY `Sid` (`Sid`),
  KEY `Cid` (`Cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `DistributesTo`
--

INSERT INTO `DistributesTo` (`Cid`, `Sid`) VALUES
(15210, 5230),
(15210, 5240),
(15217, 5240),
(15213, 5250);

-- --------------------------------------------------------

--
-- Table structure for table `GuestUser`
--

CREATE TABLE IF NOT EXISTS `GuestUser` (
  `tempID` int(4) NOT NULL,
  `UName` varchar(12) NOT NULL,
  PRIMARY KEY (`UName`),
  UNIQUE KEY `UName` (`UName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `GuestUser`
--

INSERT INTO `GuestUser` (`tempID`, `UName`) VALUES
(4, 'Guest1'),
(5, 'Guest2'),
(6, 'Guest3');

-- --------------------------------------------------------

--
-- Table structure for table `isPartof`
--

CREATE TABLE IF NOT EXISTS `isPartof` (
  `Pid1` int(5) NOT NULL,
  `Pid2` int(5) NOT NULL,
  PRIMARY KEY (`Pid1`,`Pid2`),
  KEY `Pid1` (`Pid1`),
  KEY `Pid2` (`Pid2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `isPartof`
--

INSERT INTO `isPartof` (`Pid1`, `Pid2`) VALUES
(1337, 2445);

-- --------------------------------------------------------

--
-- Table structure for table `Location`
--

CREATE TABLE IF NOT EXISTS `Location` (
  `Sid` int(5) NOT NULL,
  `Lid` varchar(255) NOT NULL,
  PRIMARY KEY (`Sid`,`Lid`),
  KEY `Sid` (`Sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Location`
--

INSERT INTO `Location` (`Sid`, `Lid`) VALUES
(5230, '9997 Rose Hills Road Whittier, CA 90601'),
(5240, '43195 Business Park Drive, Temecula, CA 92590'),
(5250, '260 Industrial Way West #1, Eatontown, NJ 07724');

-- --------------------------------------------------------

--
-- Table structure for table `Part`
--

CREATE TABLE IF NOT EXISTS `Part` (
  `Pid` int(5) NOT NULL,
  `PName` varchar(30) NOT NULL,
  `Price` float(6,2) unsigned NOT NULL,
  `Description` text NOT NULL,
  `Cid` int(5) NOT NULL,
  PRIMARY KEY (`Pid`),
  UNIQUE KEY `Pid` (`Pid`),
  KEY `Cid` (`Cid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Part`
--

INSERT INTO `Part` (`Pid`, `PName`, `Price`, `Description`, `Cid`) VALUES
(1337, 'Intel Computer Stick', 99.99, 'The Intel Computer Stick. Small, yet surprisingly powerful.', 15213),
(2445, 'Intel Core i7-4970k', 339.99, 'Devil''s Canyon Quad-Core 4.0GHz LGA 1150', 15213),
(4355, 'GSKILL Ripjaws Series', 52.99, '8 GB (2x4GB) 240-Pin DDR3 SDRAM DDR3 1600', 15210),
(6753, 'Fractal Define S', 79.90, 'Silent Computer Case', 15217);

-- --------------------------------------------------------

--
-- Table structure for table `Review`
--

CREATE TABLE IF NOT EXISTS `Review` (
  `Rid` int(11) NOT NULL AUTO_INCREMENT,
  `Rating` int(1) unsigned NOT NULL,
  `DescriptionReview` text NOT NULL,
  `Pid` int(11) NOT NULL,
  PRIMARY KEY (`Rid`,`Pid`),
  KEY `Review_ibfk_1` (`Pid`),
  KEY `Rid` (`Rid`),
  KEY `Pid` (`Pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `Review`
--

INSERT INTO `Review` (`Rid`, `Rating`, `DescriptionReview`, `Pid`) VALUES
(27, 9, 'This RAM is awesome! It outdid my expect', 4355),
(28, 8, 'Intel''s CPU i7 Processor works great for', 2445),
(29, 5, 'This item is super good!!', 6753);

-- --------------------------------------------------------

--
-- Table structure for table `Seller`
--

CREATE TABLE IF NOT EXISTS `Seller` (
  `Sid` int(5) NOT NULL,
  `SName` varchar(20) NOT NULL,
  PRIMARY KEY (`Sid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Seller`
--

INSERT INTO `Seller` (`Sid`, `SName`) VALUES
(5230, 'Newegg'),
(5240, 'Evertek'),
(5250, 'CDW');

-- --------------------------------------------------------

--
-- Table structure for table `SoldBy`
--

CREATE TABLE IF NOT EXISTS `SoldBy` (
  `Sid` int(5) NOT NULL,
  `Pid` int(5) NOT NULL,
  PRIMARY KEY (`Sid`,`Pid`),
  KEY `Sid` (`Sid`),
  KEY `Pid` (`Pid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `SoldBy`
--

INSERT INTO `SoldBy` (`Sid`, `Pid`) VALUES
(5230, 1337),
(5230, 4355),
(5240, 1337),
(5250, 6753);

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `UName` varchar(12) NOT NULL,
  PRIMARY KEY (`UName`),
  KEY `UName` (`UName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`UName`) VALUES
('Guest1'),
('Guest2'),
('Guest3'),
('MechaMan12'),
('RoboLover11'),
('Techlover15');

-- --------------------------------------------------------

--
-- Table structure for table `Writes`
--

CREATE TABLE IF NOT EXISTS `Writes` (
  `Uid` int(5) NOT NULL,
  `Rid` int(5) NOT NULL,
  `Pid` int(5) NOT NULL,
  PRIMARY KEY (`Rid`,`Pid`),
  KEY `Rid` (`Rid`),
  KEY `Pid` (`Pid`),
  KEY `Uid` (`Uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Writes`
--

INSERT INTO `Writes` (`Uid`, `Rid`, `Pid`) VALUES
(1001, 27, 4355),
(2002, 28, 2445);

-- --------------------------------------------------------

--
-- Structure for view `Account User View`
--
DROP TABLE IF EXISTS `Account User View`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `Account User View` AS select `P`.`PName` AS `PName`,`P`.`Description` AS `Description`,`P`.`Price` AS `Price`,`R`.`Rating` AS `Rating`,`R`.`DescriptionReview` AS `DescriptionReview` from (`Part` `P` join `Review` `R`);

-- --------------------------------------------------------

--
-- Structure for view `Company User View`
--
DROP TABLE IF EXISTS `Company User View`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `Company User View` AS select `C`.`CName` AS `CName`,`S`.`SName` AS `SName`,`P`.`PName` AS `PName` from ((`Company` `C` join `Seller` `S`) join `Part` `P`) where ((`C`.`CName` = 'Intel') and (`C`.`Cid` = `P`.`Cid`));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `AccountUser`
--
ALTER TABLE `AccountUser`
  ADD CONSTRAINT `AccountUser_ibfk_1` FOREIGN KEY (`UName`) REFERENCES `User` (`UName`);

--
-- Constraints for table `Buys`
--
ALTER TABLE `Buys`
  ADD CONSTRAINT `Buys_ibfk_3` FOREIGN KEY (`Pid`) REFERENCES `Part` (`Pid`),
  ADD CONSTRAINT `Buys_ibfk_2` FOREIGN KEY (`UName`) REFERENCES `User` (`UName`);

--
-- Constraints for table `DistributesTo`
--
ALTER TABLE `DistributesTo`
  ADD CONSTRAINT `DistributesTo_ibfk_2` FOREIGN KEY (`Sid`) REFERENCES `Seller` (`Sid`),
  ADD CONSTRAINT `DistributesTo_ibfk_1` FOREIGN KEY (`Cid`) REFERENCES `Company` (`Cid`);

--
-- Constraints for table `GuestUser`
--
ALTER TABLE `GuestUser`
  ADD CONSTRAINT `GuestUser_ibfk_1` FOREIGN KEY (`UName`) REFERENCES `User` (`UName`);

--
-- Constraints for table `isPartof`
--
ALTER TABLE `isPartof`
  ADD CONSTRAINT `isPartof_ibfk_2` FOREIGN KEY (`Pid2`) REFERENCES `Part` (`Pid`),
  ADD CONSTRAINT `isPartof_ibfk_1` FOREIGN KEY (`Pid1`) REFERENCES `Part` (`Pid`);

--
-- Constraints for table `Location`
--
ALTER TABLE `Location`
  ADD CONSTRAINT `Location_ibfk_1` FOREIGN KEY (`Sid`) REFERENCES `Seller` (`Sid`) ON DELETE CASCADE;

--
-- Constraints for table `Part`
--
ALTER TABLE `Part`
  ADD CONSTRAINT `Part_ibfk_1` FOREIGN KEY (`Cid`) REFERENCES `Company` (`Cid`);

--
-- Constraints for table `Review`
--
ALTER TABLE `Review`
  ADD CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`Pid`) REFERENCES `Part` (`Pid`);

--
-- Constraints for table `SoldBy`
--
ALTER TABLE `SoldBy`
  ADD CONSTRAINT `SoldBy_ibfk_2` FOREIGN KEY (`Pid`) REFERENCES `Part` (`Pid`),
  ADD CONSTRAINT `SoldBy_ibfk_1` FOREIGN KEY (`Sid`) REFERENCES `Seller` (`Sid`);

--
-- Constraints for table `Writes`
--
ALTER TABLE `Writes`
  ADD CONSTRAINT `Writes_ibfk_2` FOREIGN KEY (`Pid`) REFERENCES `Part` (`Pid`),
  ADD CONSTRAINT `Writes_ibfk_1` FOREIGN KEY (`Rid`) REFERENCES `Review` (`Rid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
