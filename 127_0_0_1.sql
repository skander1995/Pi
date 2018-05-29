-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mar. 29 mai 2018 à 03:10
-- Version du serveur :  5.7.21
-- Version de PHP :  7.1.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `espentreaideintegre`
--
CREATE DATABASE IF NOT EXISTS `espentreaideintegre` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `espentreaideintegre`;

-- --------------------------------------------------------

--
-- Structure de la table `aide`
--

DROP TABLE IF EXISTS `aide`;
CREATE TABLE IF NOT EXISTS `aide` (
  `idPub` int(11) NOT NULL AUTO_INCREMENT,
  `datepub` date NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `document` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `section` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `matiere` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPub`),
  KEY `IDX_D99184A1362A540B` (`idUsr`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `aide`
--

INSERT INTO `aide` (`idPub`, `datepub`, `description`, `document`, `section`, `matiere`, `idUsr`) VALUES
(20, '2018-05-21', 'qksdkjsd', 'f_5b02c544f02a0.pdf', '3éme', 'qsd', 0),
(21, '2018-05-21', 'test final', 'f_5b02e38c99de4.sql', '4éme', 'java', 0),
(23, '2018-05-21', 'test web integration', '0580695d0ce0f38cf15fc7f3d1101e1c.pdf', 'educ', 'educ', 1),
(24, '2018-05-22', 'qksjd', 'cb24aaea820666900f8e8124f5424eeb.png', 'qsdjqsd', 'qskldjqs', 1),
(25, '2018-05-22', 'cours java', 'f_5b03a6606a637.pdf', '3A', 'Java', 1),
(26, '2018-05-22', 'TD Java', '9e1b44a1e58cdbaff34d75cd05c45c2b.pdf', '3A', 'Java', 1),
(27, '2018-05-22', 'Td SQL', '7f237ec54dcaa52df4a05268b23ce39a.pdf', '3A', 'SQL', 1);

-- --------------------------------------------------------

--
-- Structure de la table `colocation`
--

DROP TABLE IF EXISTS `colocation`;
CREATE TABLE IF NOT EXISTS `colocation` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `idUsr` int(11) DEFAULT NULL,
  `DATEPUB` datetime DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `LIEU` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LOYERMENSUEL` double DEFAULT NULL,
  `IMGCOUVERTURE` text COLLATE utf8_unicode_ci,
  `NBCHAMBRE` int(11) DEFAULT NULL,
  `COMMODITE` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DATEDISPONIBILITE` date DEFAULT NULL,
  `ID_VILLE` int(11) DEFAULT NULL,
  `titre` tinytext COLLATE utf8_unicode_ci,
  `rules` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wifi` tinyint(1) DEFAULT NULL,
  `climatiseur` tinyint(1) DEFAULT NULL,
  `chauffagecentrale` tinyint(1) DEFAULT NULL,
  `tv` tinyint(1) DEFAULT NULL,
  `camerasurvaillance` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`),
  KEY `FK_SITUER_COLOC` (`ID_VILLE`),
  KEY `IDX_613CCFD2362A540B` (`idUsr`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `colocation`
--

INSERT INTO `colocation` (`ID_PUB`, `idUsr`, `DATEPUB`, `DESCRIPTION`, `LIEU`, `LOYERMENSUEL`, `IMGCOUVERTURE`, `NBCHAMBRE`, `COMMODITE`, `DATEDISPONIBILITE`, `ID_VILLE`, `titre`, `rules`, `wifi`, `climatiseur`, `chauffagecentrale`, `tv`, `camerasurvaillance`) VALUES
(1, 0, NULL, 'akjhqsd', 'Nasr', 53, NULL, NULL, NULL, '2018-05-29', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 0, '2018-05-31 00:00:00', 'azdq', 'Ariana soghra', 52, NULL, NULL, NULL, '2018-05-31', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 1, '2018-06-01 00:00:00', 'bien', 'Ariana soghra', 170, NULL, NULL, NULL, '2018-06-01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `ID_COM` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PUB` int(11) NOT NULL,
  `CONTENU_COM` text COLLATE utf8_unicode_ci,
  `DATE_COM` datetime DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `photoprofile` longtext COLLATE utf8_unicode_ci,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_COM`),
  KEY `IDX_67F068BC362A540B` (`idUsr`),
  KEY `FK_CONTENIR` (`ID_PUB`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commentaire`
--

INSERT INTO `commentaire` (`ID_COM`, `ID_PUB`, `CONTENU_COM`, `DATE_COM`, `username`, `photoprofile`, `idUsr`) VALUES
(6, 9, 'com', '2018-05-21 03:03:57', NULL, NULL, 1),
(7, 11, 'add comm', '2018-05-21 03:12:18', NULL, NULL, 1),
(8, 11, 'comm', '2018-05-21 03:13:21', NULL, NULL, 1),
(9, 3, 'comm', '2018-05-21 10:49:30', NULL, NULL, 1),
(11, 3, 'root com', '2018-05-21 12:08:56', NULL, NULL, 0),
(12, 10, 'hello', '2018-05-21 12:45:06', NULL, NULL, 0),
(13, 20, 'qsd', '2018-05-22 02:55:55', NULL, NULL, 1),
(14, 24, 'bien', '2018-05-22 06:04:28', NULL, NULL, 1),
(15, 10, 'bien', '2018-05-22 06:08:23', NULL, NULL, 1),
(16, 20, 'bien ...', '2018-05-22 06:10:11', NULL, NULL, 1),
(18, 26, 'ok', '2018-05-22 00:00:00', NULL, NULL, 1),
(19, 26, 'bien', '2018-05-22 00:00:00', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire_ques`
--

DROP TABLE IF EXISTS `commentaire_ques`;
CREATE TABLE IF NOT EXISTS `commentaire_ques` (
  `ID_COM` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PUB` int(11) NOT NULL,
  `CONTENU_COM` text COLLATE utf8_unicode_ci,
  `DATE_COM` datetime DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `photoprofile` longtext COLLATE utf8_unicode_ci,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_COM`),
  KEY `IDX_9883C968362A540B` (`idUsr`),
  KEY `FK_CONTENIR` (`ID_PUB`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `commentaire_ques`
--

INSERT INTO `commentaire_ques` (`ID_COM`, `ID_PUB`, `CONTENU_COM`, `DATE_COM`, `username`, `photoprofile`, `idUsr`) VALUES
(6, 9, 'test', '2018-05-21 03:45:13', NULL, NULL, 1),
(7, 9, 'helo', '2018-05-21 03:46:00', NULL, NULL, 1),
(8, 9, 'hhhhh', '2018-05-21 10:48:51', NULL, NULL, 1),
(9, 10, 'evaluation1', '2018-05-21 00:00:00', NULL, NULL, 1),
(10, 13, 'com test final', '2018-05-21 00:00:00', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Structure de la table `covoiturage`
--

DROP TABLE IF EXISTS `covoiturage`;
CREATE TABLE IF NOT EXISTS `covoiturage` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USR` int(11) DEFAULT NULL,
  `DATEPUB` date DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `ETAT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LIEUDEPART` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LIEUARRIVE` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DATEDEPART` date DEFAULT NULL,
  `PRIX` double DEFAULT NULL,
  `CHECKPOINTS` text COLLATE utf8_unicode_ci,
  `NBPLACE` int(11) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `ID_VILLE_ARR` int(11) DEFAULT NULL,
  `VIL_ID_VILLE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`),
  KEY `FK_AVOIR_ARRIVER` (`ID_VILLE_ARR`),
  KEY `FK_AVOIR_DEPART` (`VIL_ID_VILLE`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `covoiturage`
--

INSERT INTO `covoiturage` (`ID_PUB`, `ID_USR`, `DATEPUB`, `DESCRIPTION`, `ETAT`, `LIEUDEPART`, `LIEUARRIVE`, `DATEDEPART`, `PRIX`, `CHECKPOINTS`, `NBPLACE`, `rating`, `ID_VILLE_ARR`, `VIL_ID_VILLE`) VALUES
(15, 1, NULL, 'integration', NULL, 'Ben Arous', 'La Manouba', '2018-05-24', 58, NULL, 1, NULL, NULL, NULL),
(16, 4, NULL, 'integration', NULL, 'Jendouba', 'Ariana', '2018-05-22', 10, NULL, 2, NULL, NULL, NULL),
(17, 4, NULL, 'integration2018', NULL, 'Kebili', 'Le Kef', '2018-05-22', 12, NULL, 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `evaluaion`
--

DROP TABLE IF EXISTS `evaluaion`;
CREATE TABLE IF NOT EXISTS `evaluaion` (
  `ID_EV` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USR` int(11) NOT NULL,
  `ID_PUB` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  PRIMARY KEY (`ID_EV`),
  UNIQUE KEY `ID_USR` (`ID_USR`,`ID_PUB`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `evaluaion`
--

INSERT INTO `evaluaion` (`ID_EV`, `ID_USR`, `ID_PUB`, `note`) VALUES
(1, 0, 2, 5),
(2, 0, 7, 5),
(3, 0, 3, 5),
(5, 0, 10, 4),
(6, 1, 20, 3),
(7, 1, 24, 3),
(9, 1, 26, 3);

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

DROP TABLE IF EXISTS `evenement`;
CREATE TABLE IF NOT EXISTS `evenement` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `DATEPUB` date DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `ETAT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOM_EVENT` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DATEDEBUT` date DEFAULT NULL,
  `DATEFIN` date DEFAULT NULL,
  `LIEU` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AFFICHE` text COLLATE utf8_unicode_ci,
  `place_dispo` int(11) NOT NULL,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `evenement`
--

INSERT INTO `evenement` (`ID_PUB`, `DATEPUB`, `DESCRIPTION`, `ETAT`, `NOM_EVENT`, `DATEDEBUT`, `DATEFIN`, `LIEU`, `AFFICHE`, `place_dispo`, `idUsr`) VALUES
(1, '2018-05-20', 'ref', 'dispo', 'hello', '2018-05-27', '2018-05-27', 'tunis', 'http://localhost/espentreaide/uploadable/images/THC_facebook.jpg', 3, 0);

-- --------------------------------------------------------

--
-- Structure de la table `evenements`
--

DROP TABLE IF EXISTS `evenements`;
CREATE TABLE IF NOT EXISTS `evenements` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `DATEPUB` date DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `ETAT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOM_EVENT` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DATEDEBUT` date DEFAULT NULL,
  `DATEFIN` date DEFAULT NULL,
  `LIEU` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AFFICHE` text COLLATE utf8_unicode_ci,
  `place_dispo` int(11) NOT NULL,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`),
  KEY `IDX_E10AD400362A540B` (`idUsr`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `evenements`
--

INSERT INTO `evenements` (`ID_PUB`, `DATEPUB`, `DESCRIPTION`, `ETAT`, `NOM_EVENT`, `DATEDEBUT`, `DATEFIN`, `LIEU`, `AFFICHE`, `place_dispo`, `idUsr`) VALUES
(8, '2018-05-22', '7afla behya', 'dispo', '7afla', '2018-05-23', '2018-05-24', 'esprit', 'http://localhost/espentreaide/uploadable/images/IMG_3949.JPG', 25, 1),
(18, '2018-05-22', 'Organisée par Esprit', NULL, 'Gala fin d\'année', '2018-07-01', '2018-07-01', 'Esprit Lagazelle', 'http://localhost/espentreaide/uploadable/images/party.jpg', 250, 1),
(19, '2018-05-22', 'je vous invite', 'dispo', 'Soutenance', '2018-05-24', '2018-05-24', 'esprit charguia', 'http://localhost/espentreaide/uploadable/images/C.jpg', 20, 1);

-- --------------------------------------------------------

--
-- Structure de la table `gouvernerat`
--

DROP TABLE IF EXISTS `gouvernerat`;
CREATE TABLE IF NOT EXISTS `gouvernerat` (
  `ID_GOV` int(11) NOT NULL AUTO_INCREMENT,
  `LIB_GOV` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID_GOV`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `gouvernerat`
--

INSERT INTO `gouvernerat` (`ID_GOV`, `LIB_GOV`) VALUES
(1, 'tunis'),
(2, 'sousse');

-- --------------------------------------------------------

--
-- Structure de la table `passer`
--

DROP TABLE IF EXISTS `passer`;
CREATE TABLE IF NOT EXISTS `passer` (
  `ID_PUB` int(11) NOT NULL,
  `ID_VILLE` int(11) NOT NULL,
  PRIMARY KEY (`ID_PUB`,`ID_VILLE`),
  KEY `IDX_970EA416942ABC67` (`ID_PUB`),
  KEY `IDX_970EA4166CD90189` (`ID_VILLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE IF NOT EXISTS `question` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `DATEPUB` date DEFAULT NULL,
  `SUJET` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `ETAT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idUsr` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`),
  KEY `IDX_B6F7494E362A540B` (`idUsr`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `question`
--

INSERT INTO `question` (`ID_PUB`, `DATEPUB`, `SUJET`, `DESCRIPTION`, `ETAT`, `idUsr`) VALUES
(10, '2018-05-21', 'test fin', 'test fin', NULL, 1),
(13, '2018-05-21', NULL, 'test final', NULL, 1),
(14, '2018-05-22', NULL, 'hello', NULL, 1),
(15, '2018-05-22', NULL, 'integration? 2018', NULL, 4);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `ID_PUB` int(11) NOT NULL AUTO_INCREMENT,
  `DATEPUB` date DEFAULT NULL,
  `SUJET_REC` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `ETAT` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPE_REC` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `USE_ID_USR` int(11) DEFAULT NULL,
  `ID_USR` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_PUB`),
  KEY `FK_CONCERNE` (`ID_USR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `code` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USR` int(11) NOT NULL,
  `ID_PUB` int(11) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE IF NOT EXISTS `reservations` (
  `code` int(11) NOT NULL AUTO_INCREMENT,
  `idUsr` int(11) DEFAULT NULL,
  `idPub` int(11) DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `IDX_4DA239362A540B` (`idUsr`),
  KEY `IDX_4DA2397B0C2102` (`idPub`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `situer_event`
--

DROP TABLE IF EXISTS `situer_event`;
CREATE TABLE IF NOT EXISTS `situer_event` (
  `ID_PUB_S` int(11) NOT NULL,
  `ID_VILLE_S` int(11) NOT NULL,
  PRIMARY KEY (`ID_PUB_S`,`ID_VILLE_S`),
  KEY `IDX_A5A6B6EB66F3C5C7` (`ID_PUB_S`),
  KEY `IDX_A5A6B6EB13DE2C67` (`ID_VILLE_S`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(180) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) DEFAULT '0',
  `salt` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `confirmation_token` varchar(180) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci COMMENT '(DC2Type:array)',
  `nom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prenom` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `datenaissance` date DEFAULT NULL,
  `photoprofile` longtext COLLATE utf8_unicode_ci,
  `telephone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `profile_picture` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `socialid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_usr` varchar(60) COLLATE utf8_unicode_ci DEFAULT 'user',
  `connected` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQ_957A647992FC23A8` (`username_canonical`),
  UNIQUE KEY `UNIQ_957A6479A0D96FBF` (`email_canonical`),
  UNIQUE KEY `UNIQ_957A6479C05FB297` (`confirmation_token`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `confirmation_token`, `password_requested_at`, `roles`, `nom`, `prenom`, `datenaissance`, `photoprofile`, `telephone`, `profile_picture`, `created_at`, `updated_at`, `socialid`, `type_usr`, `connected`) VALUES
(0, 'root', 'root', 'k@dj.com', 'k@dj.com', 1, NULL, '$2y$13$EdFgfGnz4dYiXntVrOIx1.nexa/tDR3o9hkmAz9mMLdBnbxLcfHsy', '2018-05-22 11:44:30', NULL, NULL, 'a:2:{i:0;s:5:\"ADMIN\";i:1;s:10:\"ROLE_ADMIN\";}', 'root', 'root', '2018-05-04', 'root.png', NULL, 'root.png', NULL, '2018-05-22 11:44:30', NULL, 'admin', 0),
(1, 'emira', 'emira', 'ak@dj.com', 'ak@dj.com', 1, NULL, '$2a$13$EdFgfGnz4dYiXntVrOIx1.nexa/tDR3o9hkmAz9mMLdBnbxLcfHsy', '2018-05-28 17:10:57', NULL, NULL, 'a:0:{}', 'obba', 'emira', '2018-05-04', 'emira.png', NULL, 'skander.png', NULL, '2018-05-28 17:10:57', NULL, 'user', 1),
(2, 'skander', 'skanderbenothman', 'sk@dj.com', 'sk@dj.com', 1, NULL, '$2y$13$EdFgfGnz4dYiXntVrOIx1.nexa/tDR3o9hkmAz9mMLdBnbxLcfHsy', NULL, NULL, NULL, 'a:0:{}', 'benothman', 'skander', '2018-05-04', 'skander.png', NULL, 'skander.png', NULL, NULL, NULL, 'user', 0),
(3, 'roootrooot', 'roootrooot', 'root@esprit.tn', 'root@esprit.tn', 1, NULL, '$2a$13$lgXyCNiWu6bKJ1JGChR6eehzKD6kRvKRHJQ9BKlu.N5JjCnO0swlG', NULL, NULL, NULL, 'a:0:{}', 'rooot', 'rooot', '2018-05-24', 'skander.png', '23536585', 'skander.png', NULL, NULL, NULL, 'user', 0),
(4, 'elyeselyes', 'elyeselyes', 'cobwild_child@hotmail.com', 'cobwild_child@hotmail.com', 1, NULL, '$2y$13$gceCDIISKlidsQgqxI/geejNNtyQ8TJeGBzQ4rFEFRY.2LUT.R7vq', '2018-05-22 12:44:09', NULL, NULL, 'a:0:{}', 'elyes', 'elyes', '2018-06-01', NULL, '24608993', NULL, NULL, '2018-05-22 12:44:09', NULL, 'user', 1);

-- --------------------------------------------------------

--
-- Structure de la table `ville`
--

DROP TABLE IF EXISTS `ville`;
CREATE TABLE IF NOT EXISTS `ville` (
  `ID_VILLE` int(11) NOT NULL AUTO_INCREMENT,
  `NOM_VILLE` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ID_GOV` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_VILLE`),
  KEY `FK_APPARTIENT` (`ID_GOV`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `ville`
--

INSERT INTO `ville` (`ID_VILLE`, `NOM_VILLE`, `ID_GOV`) VALUES
(1, 'souse', 1),
(2, 'sousse', 2),
(3, 'beja', 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `aide`
--
ALTER TABLE `aide`
  ADD CONSTRAINT `FK_D99184A1362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `colocation`
--
ALTER TABLE `colocation`
  ADD CONSTRAINT `FK_613CCFD2362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_613CCFD26CD90189` FOREIGN KEY (`ID_VILLE`) REFERENCES `ville` (`ID_VILLE`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FK_67F068BC362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `commentaire_ques`
--
ALTER TABLE `commentaire_ques`
  ADD CONSTRAINT `FK_9883C968362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `covoiturage`
--
ALTER TABLE `covoiturage`
  ADD CONSTRAINT `FK_28C79E892500A980` FOREIGN KEY (`VIL_ID_VILLE`) REFERENCES `ville` (`ID_VILLE`),
  ADD CONSTRAINT `FK_28C79E894BF7E8B2` FOREIGN KEY (`ID_VILLE_ARR`) REFERENCES `ville` (`ID_VILLE`);

--
-- Contraintes pour la table `evenements`
--
ALTER TABLE `evenements`
  ADD CONSTRAINT `FK_E10AD400362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `passer`
--
ALTER TABLE `passer`
  ADD CONSTRAINT `FK_970EA4166CD90189` FOREIGN KEY (`ID_VILLE`) REFERENCES `ville` (`ID_VILLE`),
  ADD CONSTRAINT `FK_970EA416942ABC67` FOREIGN KEY (`ID_PUB`) REFERENCES `covoiturage` (`ID_PUB`);

--
-- Contraintes pour la table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK_B6F7494E362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `FK_CE606404D90CC96E` FOREIGN KEY (`ID_USR`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `FK_4DA239362A540B` FOREIGN KEY (`idUsr`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_4DA2397B0C2102` FOREIGN KEY (`idPub`) REFERENCES `evenements` (`ID_PUB`);

--
-- Contraintes pour la table `situer_event`
--
ALTER TABLE `situer_event`
  ADD CONSTRAINT `FK_A5A6B6EB13DE2C67` FOREIGN KEY (`ID_VILLE_S`) REFERENCES `ville` (`ID_VILLE`),
  ADD CONSTRAINT `FK_A5A6B6EB66F3C5C7` FOREIGN KEY (`ID_PUB_S`) REFERENCES `evenement` (`ID_PUB`);

--
-- Contraintes pour la table `ville`
--
ALTER TABLE `ville`
  ADD CONSTRAINT `FK_43C3D9C327B42734` FOREIGN KEY (`ID_GOV`) REFERENCES `gouvernerat` (`ID_GOV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
