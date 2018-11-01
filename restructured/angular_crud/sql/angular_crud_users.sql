-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:8889
-- Generation Time: Oct 05, 2017 at 09:24 AM
-- Server version: 5.6.35
-- PHP Version: 7.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `trycatch_tutorial`
--

-- --------------------------------------------------------

--
-- Table structure for table `angular_crud_users`
--

CREATE TABLE `angular_crud_users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `angular_crud_users`
--

INSERT INTO `angular_crud_users` (`id`, `firstname`, `lastname`, `type`, `active`, `created_at`) VALUES
(1410, 're', '2', 0, 0, '2017-08-18 17:46:20'),
(1412, 'fdsfs22', 'fdsfds', 0, 0, '2017-08-19 10:18:01'),
(1413, 'hugo', 'almeida', 0, 1, '2017-08-18 19:23:03'),
(1414, 'hajsdh', 'hjkhkh', 456, 1, '2017-08-18 19:23:30');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `angular_crud_users`
--
ALTER TABLE `angular_crud_users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `angular_crud_users`
--
ALTER TABLE `angular_crud_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1415;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
