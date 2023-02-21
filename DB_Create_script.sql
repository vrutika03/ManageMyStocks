-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ManageMyStock
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ManageMyStock
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ManageMyStock` DEFAULT CHARACTER SET utf8 ;
USE `ManageMyStock` ;

-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_USER_DETAILS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_USER_DETAILS` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(45) NOT NULL,
  `USER_EMAIL` VARCHAR(45) NOT NULL,
  `USER_PASSWORD` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `CUSTOMER_EMAIL_UNIQUE` (`USER_EMAIL` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_CRCB_CUST_BANKING`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_CRCB_CUST_BANKING` (
  `USER_BANK_ACC_NUM` VARCHAR(16) NOT NULL,
  `USER_ID` INT NOT NULL,
  PRIMARY KEY (`USER_ID`),
  CONSTRAINT `fk_custBank_custDetails`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `ManageMyStock`.`T_USER_DETAILS` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_AVAILABLE_STOCKS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_AVAILABLE_STOCKS` (
  `STOCK_ID` INT NOT NULL AUTO_INCREMENT,
  `STOCK_NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`STOCK_ID`),
  UNIQUE INDEX `STOCK_ID_UNIQUE` (`STOCK_ID` ASC) VISIBLE,
  UNIQUE INDEX `STOCK_NAME_UNIQUE` (`STOCK_NAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_USER_PORTFOLIO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_USER_PORTFOLIO` (
  `USER_ID` INT NOT NULL,
  `STOCK_ID` INT NOT NULL,
  `STOCK_PURCHASE_PRICE` DECIMAL(6,2) NOT NULL,
  `STOCK_QTY` INT NOT NULL,
  INDEX `fk_custPortfolio_stocksAvlb_idx` (`STOCK_ID` ASC) VISIBLE,
  INDEX `fk_custPortfolio_custDetails_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_custPortfolio_stocksAvlb`
    FOREIGN KEY (`STOCK_ID`)
    REFERENCES `ManageMyStock`.`T_AVAILABLE_STOCKS` (`STOCK_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_custPortfolio_custDetails`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `ManageMyStock`.`T_USER_DETAILS` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_USER_WATCHLIST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_USER_WATCHLIST` (
  `USER_ID` INT NOT NULL,
  `STOCK_ID` INT NOT NULL,
  INDEX `fk_custWatchlist_stockAvlb_idx` (`STOCK_ID` ASC) VISIBLE,
  INDEX `fk_custWatchlist_custDetails_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_custWatchlist_stockAvlb`
    FOREIGN KEY (`STOCK_ID`)
    REFERENCES `ManageMyStock`.`T_AVAILABLE_STOCKS` (`STOCK_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_custWatchlist_custDetails`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `ManageMyStock`.`T_USER_DETAILS` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_CRAD_ADMIN_DETAILS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_CRAD_ADMIN_DETAILS` (
  `ADMIN_EMAIL_ID` VARCHAR(45) NOT NULL,
  `ADMIN_PASSWORD` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ADMIN_EMAIL_ID`),
  UNIQUE INDEX `ADMIN_EMAIL_ID_UNIQUE` (`ADMIN_EMAIL_ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_USER_FUNDS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_USER_FUNDS` (
  `USER_ID` INT NOT NULL,
  `USER_AVAILABLE_FUNDS` DECIMAL(16,2) NULL,
  INDEX `fk_custFunds_custDetails_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_custFunds_custDetails`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `ManageMyStock`.`T_USER_DETAILS` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_STSP_STOCK_PRICES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_STSP_STOCK_PRICES` (
  `STOCK_ID` INT NOT NULL,
  `DATE` VARCHAR(45) NOT NULL,
  `STOCK_PRICE` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`DATE`),
  CONSTRAINT `fk_stockPrice_stockID`
    FOREIGN KEY (`STOCK_ID`)
    REFERENCES `ManageMyStock`.`T_AVAILABLE_STOCKS` (`STOCK_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ManageMyStock`.`T_MARGIN_INTEREST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ManageMyStock`.`T_MARGIN_INTEREST` (
  `USER_ID` INT NOT NULL,
  `USER_AVAILABLE_MARGIN` DECIMAL(18,2) NULL,
  `USER_USED_MARGIN` DECIMAL(16,2) NULL,
  INDEX `fk_custMargin_custDetails_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `fk_custMargin_custDetails`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `ManageMyStock`.`T_USER_DETAILS` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
