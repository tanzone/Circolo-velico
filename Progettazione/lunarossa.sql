-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mar 30, 2022 alle 10:05
-- Versione del server: 10.4.21-MariaDB
-- Versione PHP: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lunarossa`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `address`
--

CREATE TABLE `address` (
  `ID` int(11) NOT NULL,
  `Street` varchar(50) NOT NULL,
  `City` varchar(20) NOT NULL,
  `Province` varchar(2) NOT NULL,
  `Country` varchar(20) NOT NULL,
  `PostalCode` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `address`
--

INSERT INTO `address` (`ID`, `Street`, `City`, `Province`, `Country`, `PostalCode`) VALUES
(0, 'Campus Parma', 'Parma', 'PR', 'Italia', '43121');

-- --------------------------------------------------------

--
-- Struttura della tabella `boat`
--

CREATE TABLE `boat` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `length` int(11) NOT NULL,
  `storageCost` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `expireDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `boat`
--

INSERT INTO `boat` (`ID`, `name`, `length`, `storageCost`, `username`, `paid`, `expireDate`) VALUES
(45, 'lunarossa', 15, 30, 'user', 1, '2023-03-29'),
(46, 'barca 1', 5, 10, 'user', 0, NULL),
(47, 'barca 2', 50, 100, 'user', 1, '2023-03-29');

-- --------------------------------------------------------

--
-- Struttura della tabella `creditcard`
--

CREATE TABLE `creditcard` (
  `number` varchar(16) NOT NULL,
  `cvc` varchar(3) NOT NULL,
  `date` date NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `creditcard`
--

INSERT INTO `creditcard` (`number`, `cvc`, `date`, `name`, `lastname`) VALUES
('123456', '123', '2022-04-13', 'Manuel', 'Tanzi'),
('98765', '987', '2022-05-20', 'Maximiliano', 'Mamone');

-- --------------------------------------------------------

--
-- Struttura della tabella `notification`
--

CREATE TABLE `notification` (
  `ID` int(11) NOT NULL,
  `description` text DEFAULT NULL,
  `color` varchar(20) NOT NULL DEFAULT 'red'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `person`
--

CREATE TABLE `person` (
  `ID` int(11) NOT NULL,
  `Firstname` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `FiscalCode` varchar(16) NOT NULL,
  `AddressId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `person`
--

INSERT INTO `person` (`ID`, `Firstname`, `LastName`, `FiscalCode`, `AddressId`) VALUES
(0, 'User', 'user', 'user00user00', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `race`
--

CREATE TABLE `race` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `winner` varchar(50) NOT NULL DEFAULT 'TBD',
  `winnerBoat` varchar(50) NOT NULL DEFAULT 'TBD',
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `race`
--

INSERT INTO `race` (`ID`, `name`, `date`, `winner`, `winnerBoat`, `price`) VALUES
(10, 'gara 1', '2022-03-31', 'TBD', 'TBD', 1235),
(11, 'gara 2', '2022-04-07', 'TBD', 'TBD', 54),
(12, 'gara 3', '2023-03-22', 'TBD', 'TBD', 9999);

-- --------------------------------------------------------

--
-- Struttura della tabella `staff`
--

CREATE TABLE `staff` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `personId` int(11) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `staff`
--

INSERT INTO `staff` (`username`, `password`, `personId`, `admin`) VALUES
('admin', 'admin', 0, 1),
('staff', 'staff', 0, 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `transaction`
--

CREATE TABLE `transaction` (
  `ID` int(11) NOT NULL,
  `motive` varchar(50) NOT NULL,
  `total` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `method` varchar(50) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `transaction`
--

INSERT INTO `transaction` (`ID`, `motive`, `total`, `username`, `method`, `date`) VALUES
(13, 'PASS: user', 500, 'user', 'TRANSFER', '2022-03-22'),
(15, 'BOAT: lunarossa', 30, 'user', 'TRANSFER', '2022-03-29'),
(16, 'BOAT: barca 2', 100, 'user', 'TRANSFER', '2022-03-29'),
(17, 'RACE: gara 1', 1235, 'user', 'TRANSFER', '2022-03-29');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `seasonPass` date DEFAULT '2001-01-01',
  `personId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`username`, `password`, `seasonPass`, `personId`) VALUES
('user', 'user', '2023-03-22', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_card`
--

CREATE TABLE `user_card` (
  `username` varchar(50) NOT NULL,
  `number` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_card`
--

INSERT INTO `user_card` (`username`, `number`) VALUES
('user', '123456'),
('user', '98765');

-- --------------------------------------------------------

--
-- Struttura della tabella `user_notification`
--

CREATE TABLE `user_notification` (
  `notificationID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `user_race`
--

CREATE TABLE `user_race` (
  `raceID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `boatID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `boat`
--
ALTER TABLE `boat`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Boat_Username` (`username`);

--
-- Indici per le tabelle `creditcard`
--
ALTER TABLE `creditcard`
  ADD PRIMARY KEY (`number`);

--
-- Indici per le tabelle `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Person_AddressId` (`AddressId`);

--
-- Indici per le tabelle `race`
--
ALTER TABLE `race`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`username`),
  ADD KEY `Staff_personId` (`personId`);

--
-- Indici per le tabelle `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Transaction_username` (`username`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`),
  ADD KEY `User_personId` (`personId`);

--
-- Indici per le tabelle `user_card`
--
ALTER TABLE `user_card`
  ADD KEY `UserCard_username` (`username`),
  ADD KEY `UserCard_number` (`number`);

--
-- Indici per le tabelle `user_notification`
--
ALTER TABLE `user_notification`
  ADD PRIMARY KEY (`notificationID`,`username`),
  ADD KEY `user` (`username`);

--
-- Indici per le tabelle `user_race`
--
ALTER TABLE `user_race`
  ADD PRIMARY KEY (`raceID`,`username`,`boatID`),
  ADD KEY `Race_user` (`username`),
  ADD KEY `Race_boat` (`boatID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `address`
--
ALTER TABLE `address`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT per la tabella `boat`
--
ALTER TABLE `boat`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT per la tabella `notification`
--
ALTER TABLE `notification`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT per la tabella `person`
--
ALTER TABLE `person`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT per la tabella `race`
--
ALTER TABLE `race`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la tabella `transaction`
--
ALTER TABLE `transaction`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `boat`
--
ALTER TABLE `boat`
  ADD CONSTRAINT `Boat_Username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `Person_AddressId` FOREIGN KEY (`AddressId`) REFERENCES `address` (`ID`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Limiti per la tabella `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `Staff_personId` FOREIGN KEY (`personId`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `Transaction_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `User_personId` FOREIGN KEY (`personId`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `user_card`
--
ALTER TABLE `user_card`
  ADD CONSTRAINT `UserCard_number` FOREIGN KEY (`number`) REFERENCES `creditcard` (`number`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `UserCard_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `user_notification`
--
ALTER TABLE `user_notification`
  ADD CONSTRAINT `notification` FOREIGN KEY (`notificationID`) REFERENCES `notification` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `user_race`
--
ALTER TABLE `user_race`
  ADD CONSTRAINT `Race_boat` FOREIGN KEY (`boatID`) REFERENCES `boat` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Race_race` FOREIGN KEY (`raceID`) REFERENCES `race` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Race_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
