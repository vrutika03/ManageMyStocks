CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `addFunds`(p_user_name varchar(255), p_amount double)
BEGIN
IF (SELECT count(*) FROM T_USER_FUNDS WHERE USER_NAME = p_user_name) = 0  THEN  
INSERT INTO T_USER_FUNDS (USER_NAME,USER_AVAILABLE_FUNDS) VALUES (p_user_name,p_amount);
ELSE
UPDATE T_USER_FUNDS SET USER_AVAILABLE_FUNDS = USER_AVAILABLE_FUNDS + p_amount WHERE USER_NAME=p_user_name;
END IF;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `addStock`(IN name varchar(255), IN price decimal(6, 2), IN date varchar(255))
begin

     insert into CSCI5308_19_DEVINT.T_AVAILABLE_STOCKS(STOCK_NAME) values (name);
     select @id:=STOCK_ID from T_AVAILABLE_STOCKS where STOCK_NAME=name;  
     insert into CSCI5308_19_DEVINT.T_STOCK_PRICES(STOCK_ID, DATE, STOCK_PRICE) values (@id,date ,price);
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `addToWatchlist`(p_user_name varchar(255), p_stock_id int)
BEGIN
INSERT INTO T_USER_WATCHLIST  (USER_NAME,STOCK_ID) VALUES (p_user_name,p_stock_id);
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `createUser`(IN p_user_pan varchar(255), IN p_user_email varchar(255),
                                                               IN p_username varchar(255), IN p_password varchar(255))
BEGIN
insert into T_USER_DETAILS(USER_NAME,USER_EMAIL,USER_PASSWORD,USER_PAN) values(p_username,p_user_email,p_password,p_user_pan);
insert into T_MARGIN_INTEREST values(p_username,0,0);
insert into T_USER_FUNDS values(p_username,0);
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `createUser`(IN p_user_pan varchar(255), IN p_user_email varchar(255),
                                                               IN p_username varchar(255), IN p_password varchar(255))
BEGIN
insert into T_USER_DETAILS(USER_NAME,USER_EMAIL,USER_PASSWORD,USER_PAN) values(p_username,p_user_email,p_password,p_user_pan);
insert into T_MARGIN_INTEREST values(p_username,0,0);
insert into T_USER_FUNDS values(p_username,0);
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `deleteUser`(IN p_username varchar(255))
BEGIN
    delete from T_USER_FUNDS where USER_NAME=p_username;
    delete from T_MARGIN_INTEREST where USER_NAME=p_username;
    delete from T_USER_DETAILS where USER_NAME = p_username;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `getPerfomanceData`(IN p_stock_name varchar(45))
begin
    select STOCK_NAME,DATE,STOCK_PRICE from T_AVAILABLE_STOCKS inner join T_STOCK_PRICES on T_AVAILABLE_STOCKS.STOCK_ID = T_STOCK_PRICES.STOCK_ID and T_AVAILABLE_STOCKS.STOCK_NAME=p_stock_name;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `getUserFunds`(p_user_name varchar(255))
BEGIN
SELECT USER_AVAILABLE_FUNDS FROM T_USER_FUNDS WHERE USER_NAME = p_user_name;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `getUserMargin`(p_user_name varchar(255))
BEGIN
SELECT USER_AVAILABLE_MARGIN , USER_NAME , USER_USED_MARGIN FROM T_MARGIN_INTEREST WHERE USER_NAME = p_user_name;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `getUserPortFolio`(p_user_name varchar(255))
BEGIN
SELECT 
    avlbStocks.STOCK_NAME, stockPrices.STOCK_PRICE, userPortfolio.STOCK_QTY,userPortfolio.STOCK_ID
FROM
    T_USER_PORTFOLIO userPortfolio,
    T_STOCK_PRICES stockPrices,
    T_AVAILABLE_STOCKS avlbStocks
WHERE
    userPortfolio.USER_NAME = p_user_name
	AND userPortfolio.STOCK_ID = stockPrices.STOCK_ID
    AND stockPrices.DATE = (SELECT MAX(DATE) FROM T_STOCK_PRICES WHERE STOCK_ID = userPortfolio.STOCK_ID)
	AND userPortfolio.STOCK_ID = avlbStocks.STOCK_ID
    ORDER BY  userPortfolio.STOCK_ID;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `getUserWatchlist`(p_user_name varchar(255))
BEGIN
SELECT 
    avlbStocks.STOCK_ID,
    avlbStocks.STOCK_NAME,
    stockPrices.STOCK_PRICE
FROM
    T_USER_WATCHLIST userWatchList,
    T_AVAILABLE_STOCKS avlbStocks,
    T_STOCK_PRICES stockPrices
WHERE
    userWatchList.STOCK_ID = avlbStocks.STOCK_ID
		AND userWatchList.USER_NAME = p_user_name
        AND userWatchList.STOCK_ID = stockPrices.STOCK_ID
        AND stockPrices.DATE = (SELECT 
            MAX(DATE)
        FROM
            T_STOCK_PRICES
        WHERE
            STOCK_ID = userWatchList.STOCK_ID);
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `removeFromWatchlist`(p_user_name varchar(255), p_stock_id int)
BEGIN
DELETE FROM T_USER_WATCHLIST WHERE USER_NAME=p_user_name AND STOCK_ID=p_stock_id;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `removeFunds`(p_user_name varchar(255), p_amount double)
BEGIN
UPDATE T_USER_FUNDS SET USER_AVAILABLE_FUNDS = USER_AVAILABLE_FUNDS - p_amount WHERE USER_NAME=p_user_name;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `removeStock`(IN p_stock_id int)
begin
SET FOREIGN_KEY_CHECKS=0;
    delete from T_AVAILABLE_STOCKS where STOCK_ID=p_stock_id;
    SET FOREIGN_KEY_CHECKS=1;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `showAllStocks`()
begin
    SELECT s.*, p1.*
    FROM CSCI5308_19_DEVINT.T_AVAILABLE_STOCKS s
    JOIN CSCI5308_19_DEVINT.T_STOCK_PRICES p1 ON (s.STOCK_ID = p1.STOCK_ID)
    LEFT OUTER JOIN CSCI5308_19_DEVINT.T_STOCK_PRICES p2 ON (s.STOCK_ID = p2.STOCK_ID AND
        (p1.date < p2.date OR (p1.date = p2.date AND p1.STOCK_ID < p2.STOCK_ID)))
    WHERE p2.STOCK_ID IS NULL;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `showAllUsers`()
BEGIN
select * from T_USER_DETAILS;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `stockPurchase`(IN username varchar(255), IN stockid int, IN qty int,
                                                                  IN purchasePrice decimal(6, 2))
begin
	If exists(select USER_AVAILABLE_FUNDS from T_USER_FUNDS where USER_NAME=username and USER_AVAILABLE_FUNDS > (purchasePrice*qty))
	then
	    if exists(select STOCK_ID from T_USER_PORTFOLIO where stock_id = stockid and username = USER_NAME)
	    then
            update T_USER_PORTFOLIO set STOCK_QTY = T_USER_PORTFOLIO.STOCK_QTY+qty, STOCK_PURCHASE_PRICE=(((STOCK_PURCHASE_PRICE*STOCK_QTY)+(qty*purchasePrice))/(STOCK_QTY+qty)) where stock_id = stockid and username = USER_NAME;
        else
	        INSERT INTO T_USER_PORTFOLIO (USER_NAME, STOCK_PURCHASE_PRICE, STOCK_QTY, STOCK_ID) VALUES (username,purchasePrice,qty,stockid);
        end if;
	end if;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `stockSell`(IN username varchar(255), IN stockid int, IN qty int)
begin
SET SQL_SAFE_UPDATES = 0;
	If exists(select STOCK_ID from T_USER_PORTFOLIO where stock_id = stockid and username = USER_NAME and STOCK_QTY >0)
	then
		update T_USER_PORTFOLIO set STOCK_QTY = STOCK_QTY - qty where stock_id = stockid and username = USER_NAME;
		if exists(select STOCK_QTY from T_USER_PORTFOLIO where STOCK_ID=stockid and STOCK_QTY=0)
		    then
                delete from T_USER_PORTFOLIO where STOCK_ID=stockid;
        end if;
	end if;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `updateStock`(id int,price_date varchar(255),price int)
begin
    update T_STOCK_PRICES set DATE=price_date,STOCK_PRICE=price where STOCK_ID=id;
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `updateStockPrice`(IN id int(255), IN price decimal(6, 2), IN date varchar(255))
begin
    insert into  T_STOCK_PRICES values(id,date,price);
end

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `updateUser`(p_username nvarchar(255),p_role nvarchar(45))
BEGIN
update T_USER_DETAILS set USER_ROLE = p_role where USER_NAME = p_username;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `updateUserMargin`(p_user_name varchar(255),p_amount double)
BEGIN
UPDATE T_MARGIN_INTEREST set USER_AVAILABLE_MARGIN = USER_AVAILABLE_MARGIN + p_amount  WHERE USER_NAME = p_user_name;
END

CREATE DEFINER=`CSCI5308_19_DEVINT_USER`@`%` PROCEDURE `withdrawFunds`(p_user_name varchar(200), p_amount double)
BEGIN
IF (SELECT count(*) FROM T_USER_FUNDS WHERE USER_NAME = p_user_name) = 0  THEN  
INSERT INTO T_USER_FUNDS (USER_NAME,USER_AVAILABLE_FUNDS) VALUES (p_user_name,p_amount);
ELSE
UPDATE T_USER_FUNDS SET USER_AVAILABLE_FUNDS = USER_AVAILABLE_FUNDS - p_amount WHERE USER_NAME=p_user_name;
END IF;
END

