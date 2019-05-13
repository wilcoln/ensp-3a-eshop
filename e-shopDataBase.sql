/*
SQLyog Community v12.5.0 (64 bit)
MySQL - 5.6.17 : Database - eshop
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`eshop` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `eshop`;

/*Table structure for table `bonachat` */

DROP TABLE IF EXISTS `bonachat`;

CREATE TABLE `bonachat` (
  `codeBon` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dateBon` date NOT NULL,
  `montantBon` decimal(10,2) NOT NULL,
  `idFac` int(11) unsigned NOT NULL,
  `idGestionnaire` int(11) unsigned NOT NULL,
  `expirer` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`codeBon`),
  KEY `reffact` (`idFac`),
  KEY `refGest` (`idGestionnaire`),
  CONSTRAINT `factureRefBon` FOREIGN KEY (`idFac`) REFERENCES `facture` (`idFac`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `gestionnaireBon` FOREIGN KEY (`idGestionnaire`) REFERENCES `gestionnaire` (`idGest`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `categorie` */

DROP TABLE IF EXISTS `categorie`;

CREATE TABLE `categorie` (
  `idCa` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nomCa` varchar(60) NOT NULL,
  PRIMARY KEY (`idCa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `client` */

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `codeClient` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nomCli` varchar(50) NOT NULL,
  `telCli` varchar(16) DEFAULT NULL,
  `whatsap` varchar(16) DEFAULT NULL,
  `dateAbonnement` date NOT NULL,
  `pointFidelite` int(11) unsigned DEFAULT '0',
  `remise` smallint(4) NOT NULL DEFAULT '0',
  `typeCli` smallint(4) unsigned NOT NULL DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  `idVille` int(11) unsigned NOT NULL,
  PRIMARY KEY (`codeClient`),
  KEY `FK_IDVille` (`idVille`),
  CONSTRAINT `FK_IDVille` FOREIGN KEY (`idVille`) REFERENCES `ville` (`idVille`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `commande` */

DROP TABLE IF EXISTS `commande`;

CREATE TABLE `commande` (
  `idCom` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dateCom` datetime NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  `traite` tinyint(1) NOT NULL DEFAULT '0',
  `validePayer` tinyint(1) NOT NULL DEFAULT '0',
  `remise` smallint(4) unsigned NOT NULL DEFAULT '0',
  `fraisEmoney` decimal(5,2) DEFAULT '0.00',
  `fraisExpedition` decimal(5,2) NOT NULL DEFAULT '0.00',
  `codeClient` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idCom`),
  KEY `refClientG` (`codeClient`),
  CONSTRAINT `refClientG` FOREIGN KEY (`codeClient`) REFERENCES `client` (`codeClient`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `connexion` */

DROP TABLE IF EXISTS `connexion`;

CREATE TABLE `connexion` (
  `idConnexion` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `entree` datetime NOT NULL,
  `sortie` datetime DEFAULT NULL,
  `appareil` varchar(100) DEFAULT NULL,
  `so` varchar(100) DEFAULT NULL,
  `idGestionnaire` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idConnexion`),
  KEY `refGestionnaireCon` (`idGestionnaire`),
  CONSTRAINT `refGestionnaireCon` FOREIGN KEY (`idGestionnaire`) REFERENCES `gestionnaire` (`idGest`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `elementbonachat` */

DROP TABLE IF EXISTS `elementbonachat`;

CREATE TABLE `elementbonachat` (
  `qteRetourner` smallint(4) unsigned NOT NULL,
  `prixUnitaire` decimal(10,2) NOT NULL,
  `idPro` int(11) unsigned NOT NULL,
  `codeBon` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idPro`,`codeBon`),
  KEY `refBonAchat` (`codeBon`),
  KEY `refProduitPO` (`idPro`),
  CONSTRAINT `refBonAchat` FOREIGN KEY (`codeBon`) REFERENCES `bonachat` (`codeBon`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `refProduit` FOREIGN KEY (`idPro`) REFERENCES `produit` (`idPro`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `elementcommande` */

DROP TABLE IF EXISTS `elementcommande`;

CREATE TABLE `elementcommande` (
  `qteCom` int(11) unsigned NOT NULL,
  `prixUnitaire` decimal(10,2) NOT NULL,
  `idCom` int(11) unsigned NOT NULL,
  `idPro` int(11) unsigned NOT NULL,
  `taille` varchar(15) DEFAULT 'RAS',
  PRIMARY KEY (`idCom`,`idPro`),
  KEY `refProd` (`idPro`),
  KEY `refCom` (`idCom`),
  CONSTRAINT `refComm` FOREIGN KEY (`idCom`) REFERENCES `commande` (`idCom`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `refProd` FOREIGN KEY (`idPro`) REFERENCES `produit` (`idPro`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `elementfacture` */

DROP TABLE IF EXISTS `elementfacture`;

CREATE TABLE `elementfacture` (
  `qteVendu` smallint(4) unsigned NOT NULL,
  `prixUnitaire` decimal(10,2) NOT NULL,
  `idFac` int(11) unsigned NOT NULL,
  `idPro` int(11) unsigned NOT NULL,
  `codePro` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idFac`,`idPro`),
  KEY `produitRef` (`idPro`),
  KEY `factureRef` (`idFac`),
  CONSTRAINT `factureRef` FOREIGN KEY (`idFac`) REFERENCES `facture` (`idFac`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `produitRef` FOREIGN KEY (`idPro`) REFERENCES `produit` (`idPro`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `emoney` */

DROP TABLE IF EXISTS `emoney`;

CREATE TABLE `emoney` (
  `idEmoney` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `montantInferieur` decimal(10,2) NOT NULL,
  `montantSuperieur` decimal(10,2) NOT NULL,
  `taux` decimal(5,2) NOT NULL,
  PRIMARY KEY (`idEmoney`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `facture` */

DROP TABLE IF EXISTS `facture`;

CREATE TABLE `facture` (
  `idFac` int(11) unsigned NOT NULL,
  `dateFac` datetime NOT NULL,
  `typeFac` smallint(3) unsigned NOT NULL DEFAULT '0',
  `remise` smallint(3) NOT NULL DEFAULT '0',
  `fraisEmoney` decimal(5,2) DEFAULT '0.00',
  `fraisExpedition` decimal(5,2) DEFAULT '0.00',
  `montantReel` decimal(10,2) NOT NULL DEFAULT '0.00',
  `montantPaye` decimal(10,2) NOT NULL DEFAULT '0.00',
  `codeClient` int(11) unsigned DEFAULT '0',
  `idGestionnaire` int(11) unsigned NOT NULL,
  `codeBonAchat` int(11) unsigned DEFAULT NULL,
  `typePaiement` int(11) DEFAULT '0',
  PRIMARY KEY (`idFac`),
  KEY `gestionnaireFacture` (`idGestionnaire`),
  CONSTRAINT `gestionnaireFacture` FOREIGN KEY (`idGestionnaire`) REFERENCES `gestionnaire` (`idGest`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `fournisseur` */

DROP TABLE IF EXISTS `fournisseur`;

CREATE TABLE `fournisseur` (
  `idFour` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nomFour` varchar(40) NOT NULL DEFAULT 'Inconnu',
  `adresse` varchar(100) DEFAULT 'RAS',
  `tel1` varchar(15) DEFAULT '00000',
  `whatsap` varchar(15) DEFAULT '00000',
  `siteWeb` varchar(60) DEFAULT 'RAS',
  PRIMARY KEY (`idFour`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `gestionnaire` */

DROP TABLE IF EXISTS `gestionnaire`;

CREATE TABLE `gestionnaire` (
  `idGest` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nomGest` varchar(40) NOT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(80) NOT NULL,
  `typeGest` tinyint(4) NOT NULL,
  `cni` varchar(30) DEFAULT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idGest`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `gestionstock` */

DROP TABLE IF EXISTS `gestionstock`;

CREATE TABLE `gestionstock` (
  `idGestionStock` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dateGest` date NOT NULL,
  `typeGest` tinyint(1) NOT NULL,
  `description` tinytext,
  `montant` decimal(10,2) DEFAULT NULL,
  `idGestionnaire` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idGestionStock`),
  KEY `gestionnaireRef` (`idGestionnaire`),
  CONSTRAINT `gestionnaireRef` FOREIGN KEY (`idGestionnaire`) REFERENCES `gestionnaire` (`idGest`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `photo` */

DROP TABLE IF EXISTS `photo`;

CREATE TABLE `photo` (
  `idPhoto` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lienPhoto` varchar(200) NOT NULL,
  `couleur` varchar(20) NOT NULL,
  `idPro` int(11) unsigned NOT NULL,
  `photoDefaut` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idPhoto`),
  KEY `prod` (`idPro`),
  CONSTRAINT `prod` FOREIGN KEY (`idPro`) REFERENCES `produit` (`idPro`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `produit` */

DROP TABLE IF EXISTS `produit`;

CREATE TABLE `produit` (
  `idPro` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `codePro` int(11) unsigned NOT NULL,
  `nomPro` varchar(40) DEFAULT NULL,
  `quantite` int(8) unsigned NOT NULL DEFAULT '0',
  `prixVente` decimal(10,2) NOT NULL,
  `description` tinytext,
  `codeFour` varchar(15) DEFAULT '000000',
  `idFour` int(11) unsigned NOT NULL,
  `idCa` int(11) unsigned NOT NULL,
  `prixAchat` decimal(10,2) DEFAULT '0.00',
  `dateDernierArr` date DEFAULT NULL,
  `dateCreation` date NOT NULL,
  `PrixAchatBis` decimal(10,2) NOT NULL DEFAULT '0.00',
  `supprimer` tinyint(1) NOT NULL DEFAULT '0',
  `taille` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idPro`),
  UNIQUE KEY `codeProUnique` (`codePro`),
  KEY `fourni` (`idFour`),
  KEY `catego` (`idCa`),
  CONSTRAINT `catego` FOREIGN KEY (`idCa`) REFERENCES `categorie` (`idCa`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fourni` FOREIGN KEY (`idFour`) REFERENCES `fournisseur` (`idFour`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sms` */

DROP TABLE IF EXISTS `sms`;

CREATE TABLE `sms` (
  `idSMS` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` varchar(160) NOT NULL,
  `dateSMS` datetime NOT NULL,
  `TelClient` varchar(16) NOT NULL,
  `codeClient` int(11) unsigned NOT NULL DEFAULT '0',
  `typeSMS` smallint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idSMS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `qteStock` int(11) unsigned NOT NULL,
  `idPro` int(11) unsigned NOT NULL,
  `idGestionStock` int(11) unsigned NOT NULL,
  PRIMARY KEY (`idPro`,`idGestionStock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `ville` */

DROP TABLE IF EXISTS `ville`;

CREATE TABLE `ville` (
  `idVille` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nomVille` varchar(40) NOT NULL,
  `agenceExpedition` varchar(160) DEFAULT NULL,
  `fraisExpedition` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`idVille`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `whatsapmessage` */

DROP TABLE IF EXISTS `whatsapmessage`;

CREATE TABLE `whatsapmessage` (
  `idWMessage` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message` text,
  `dateMessage` datetime NOT NULL,
  `codeClient` int(11) unsigned DEFAULT '0',
  `typeMessage` int(11) NOT NULL DEFAULT '0',
  `telClient` varchar(15) NOT NULL,
  PRIMARY KEY (`idWMessage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
