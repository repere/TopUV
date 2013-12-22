-- phpMyAdmin SQL Dump
-- version 3.4.3.2
-- http://www.phpmyadmin.net
--
-- Client: 127.0.0.1
-- Généré le : Dim 22 Décembre 2013 à 15:48
-- Version du serveur: 5.5.15
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `topuv`
--

-- --------------------------------------------------------

--
-- Structure de la table `note`
--

CREATE TABLE IF NOT EXISTS `note` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `id_user` int(12) NOT NULL,
  `id_uv` int(12) NOT NULL,
  `note` float NOT NULL,
  `comment` text COLLATE armscii8_bin NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_uv` (`id_uv`),
  KEY `id_uv_2` (`id_uv`)
) ENGINE=InnoDB  DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin AUTO_INCREMENT=12 ;

--
-- Contenu de la table `note`
--

INSERT INTO `note` (`id`, `id_user`, `id_uv`, `note`, `comment`, `date`) VALUES
(8, 23, 7, 7, 'good', '2013-12-22'),
(9, 23, 7, 6, 'good', '2013-12-22'),
(10, 23, 2, 6, 'good', '2013-12-22'),
(11, 23, 2, 5, 'good', '2013-12-22');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) COLLATE armscii8_bin NOT NULL,
  `last_name` varchar(45) COLLATE armscii8_bin NOT NULL,
  `email` varchar(45) COLLATE armscii8_bin NOT NULL,
  `login` varchar(20) COLLATE armscii8_bin NOT NULL,
  `password` varchar(45) COLLATE armscii8_bin NOT NULL,
  `salt` varchar(20) COLLATE armscii8_bin NOT NULL,
  `token` varchar(32) COLLATE armscii8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin AUTO_INCREMENT=24 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `login`, `password`, `salt`, `token`) VALUES
(21, 'OURNID', 'Mohamed', 'mohamed.ournid@utt.fr', 'ournidmo', 'bRCMIPRvfnLC4wdtiKcRSYpryic2NTA0NzAxY2E3', '6504701ca7', '52a44a61b8c609.77611679'),
(23, 'GEMIN', 'Antoine', 'antoine.gemin@utt.fr', 'antoinegem', 'QbbnId1dI4g57/R780Br+cw0BF43YzQ5YTk4MGMy', '7c49a980c2', '52a44b3b244b05.46232132');

-- --------------------------------------------------------

--
-- Structure de la table `uv`
--

CREATE TABLE IF NOT EXISTS `uv` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(12) COLLATE armscii8_bin NOT NULL,
  `designation` varchar(100) COLLATE armscii8_bin NOT NULL,
  `categorie` varchar(60) COLLATE armscii8_bin NOT NULL,
  `credit` int(10) NOT NULL,
  `description` text COLLATE armscii8_bin NOT NULL,
  `note` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin AUTO_INCREMENT=12 ;

--
-- Contenu de la table `uv`
--

INSERT INTO `uv` (`id`, `code`, `designation`, `categorie`, `credit`, `description`, `note`) VALUES
(1, 'IF26', 'Conception securisee d''applications : Web Mobile et Smartphone', 'Techniques et Methodes', 6, 'Apprentissage des methodes et outils de conception d’applications securisees appliques au developpement pour smartphones', 0),
(2, 'IF01', 'Theorie et codage de l’information', 'Connaissances Scientifiques', 6, 'se familiariser avec certains aspects des nouvelles technologies de l''information dans un cadre theorique adapte', 5.5),
(3, 'LE01', 'Anglais - niveau elementaire / Structures de base', 'Expression et Communication', 2, '', 0),
(4, 'LE02', 'Anglais - niveau moyen', 'Expression et Communication', 2, '', 0),
(5, 'NF14', 'Algoritmique', 'Techniques et Methodes', 6, '', 0),
(6, 'NF05', 'Introduction au langage C', 'Techniques et Methodes', 6, '', 0),
(7, 'GS15', 'Cryptologie et signature electronique', 'Connaissances Scientifiques', 6, '', 6.5),
(8, 'GE21', 'Entreprise et le droit', 'Management de Entreprise', 4, '', 0),
(9, 'GE28', 'Droit du commerce et des affaires', 'Management de Entreprise', 4, '', 0),
(10, 'SP02', 'Animateur sportif', 'Culture et Technologie', 4, '', 0),
(11, 'SP03', 'Animateur qualifie', 'Culture et Technologie', 4, '', 0);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `note_ibfk_2` FOREIGN KEY (`id_uv`) REFERENCES `uv` (`id`),
  ADD CONSTRAINT `note_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
