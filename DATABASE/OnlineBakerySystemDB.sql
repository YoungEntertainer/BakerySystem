-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.25 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for bakery
DROP DATABASE IF EXISTS `bakery`;
CREATE DATABASE IF NOT EXISTS `bakery` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bakery`;

-- Dumping structure for table bakery.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `CategoryID` int NOT NULL AUTO_INCREMENT,
  `Description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`CategoryID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CustomerName` varchar(30) NOT NULL,
  `customerIDNo` varchar(20) NOT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `joinDate` datetime NOT NULL,
  `addressOne` varchar(30) NOT NULL,
  `addressTwo` varchar(30) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `Comment` varchar(1024) DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for procedure bakery.fetch_all_orders
DROP PROCEDURE IF EXISTS `fetch_all_orders`;
DELIMITER //
CREATE PROCEDURE `fetch_all_orders`()
BEGIN
		SELECT
			o.ID as order_id, Customer_ID, CustomerName, DatePlaced, PickupTime, Fulfilled, o.Comment
		FROM `Order` o
		JOIN Customer c ON c.ID = o.Customer_ID
		ORDER BY PickupTime;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_all_products
DROP PROCEDURE IF EXISTS `fetch_all_products`;
DELIMITER //
CREATE PROCEDURE `fetch_all_products`()
BEGIN
    SELECT p.ID as product_id, Name
    FROM Product p;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_all_sales
DROP PROCEDURE IF EXISTS `fetch_all_sales`;
DELIMITER //
CREATE PROCEDURE `fetch_all_sales`()
BEGIN
    SELECT 
            s.ID as report_id, 
            s.Date,
            s.Comment,
            s.Hours, 
            sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale) as profit, 
            sum(QuantitySold * PriceAtSale) as revenue
		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON Sales_Report_ID = s.ID
		JOIN Product p ON product_id = p.ID
		GROUP BY report_id, Date
		ORDER BY Date DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_all_sales_in_range
DROP PROCEDURE IF EXISTS `fetch_all_sales_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_all_sales_in_range`(
	IN `startDate` DATE,
	IN `endDate` DATE
)
BEGIN
		SELECT *
		FROM Sales_Report sp
        WHERE sp.DatePlaced >= startDate AND sp.DatePlaced <= endDate 
        ORDER BY sp.DatePlaced DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_customers_keyword
DROP PROCEDURE IF EXISTS `fetch_customers_keyword`;
DELIMITER //
CREATE PROCEDURE `fetch_customers_keyword`(IN keyWord VARCHAR(52))
BEGIN
		SELECT *
		FROM Customer c
        WHERE CustomerName LIKE CONCAT('%', keyWord, '%') 
        OR customerIDNo LIKE CONCAT('%',keyWord,'%') 
        OR phoneNumber LIKE CONCAT('%',keyWord,'%') 
        OR addressOne LIKE CONCAT('%',keyWord,'%') 
        ORDER BY joinDate DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_customer_favorites
DROP PROCEDURE IF EXISTS `fetch_customer_favorites`;
DELIMITER //
CREATE PROCEDURE `fetch_customer_favorites`(IN cust_id INT)
BEGIN
SELECT
	c.ID as id,
	p.Name as name,
	SUM(od.Quantity) as total
		FROM bakery.Customer c
			JOIN bakery.`Order` o ON c.ID = o.Customer_ID
			JOIN bakery.Order_Details od ON o.ID = od.order_id
			JOIN bakery.Product p ON od.product_id = p.ID
		WHERE c.ID = cust_id
		GROUP BY
			c.ID, p.Name
		ORDER BY total DESC
        limit 7;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_customer_info
DROP PROCEDURE IF EXISTS `fetch_customer_info`;
DELIMITER //
CREATE PROCEDURE `fetch_customer_info`(IN cust_id INT)
BEGIN
	SELECT *
    FROM Customer
    WHERE ID = cust_id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_customer_orders
DROP PROCEDURE IF EXISTS `fetch_customer_orders`;
DELIMITER //
CREATE PROCEDURE `fetch_customer_orders`(IN cust_id INT)
BEGIN
	SELECT 
		o.ID, DatePlaced, Fulfilled, o.Comment, SUM(Quantity * Price) as Total
	FROM `Order` o
    JOIN Order_Details od ON od.order_id = o.ID
    JOIN Product p on p.ID = od.product_id
    WHERE Customer_ID = cust_id
    GROUP BY order_id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_customer_points
DROP PROCEDURE IF EXISTS `fetch_customer_points`;
DELIMITER //
CREATE PROCEDURE `fetch_customer_points`(IN cust_id INT)
BEGIN
SELECT min(ID) as ID, sum(total) as total
    FROM(
        SELECT pt.ID ,sum(p.Amount) as total
        From bakery.Customer c
            JOIN bakery.`Order` o ON o.Customer_ID = c.ID
            JOIN bakery.Payment p ON p.order_id = o.ID
            JOIN bakery.Payment_Type pt ON pt.ID = p.Payment_Type_ID
            WHERE c.ID = cust_id
            GROUP BY pt.ID) as T
GROUP BY (case when ID in (1,2) then 1 else ID end);
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_ingredients_keyword
DROP PROCEDURE IF EXISTS `fetch_ingredients_keyword`;
DELIMITER //
CREATE PROCEDURE `fetch_ingredients_keyword`(IN keyWord VARCHAR(52))
BEGIN
		SELECT *
		FROM Ingredient i
        WHERE i.Name LIKE CONCAT('%', keyWord, '%') 
        OR i.PricePerKG LIKE CONCAT('%',keyWord,'%') 
        OR i.Note LIKE CONCAT('%',keyWord,'%');
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_ingredient_info
DROP PROCEDURE IF EXISTS `fetch_ingredient_info`;
DELIMITER //
CREATE PROCEDURE `fetch_ingredient_info`(IN Ingredient_ID INT)
BEGIN
	SELECT *
    FROM Ingredient
    WHERE ID = Ingredient_ID;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_ingredient_products
DROP PROCEDURE IF EXISTS `fetch_ingredient_products`;
DELIMITER //
CREATE PROCEDURE `fetch_ingredient_products`(IN ing_id INT)
BEGIN
SELECT p.ID, p.Name, p.price, p.FoodCost, p.TimeCost, ri.grams
FROM Product p
JOIN Recipe r ON r.product_id = p.ID 
JOIN Recipe_Ingredient ri ON ri.Recipe_ID = r.product_id
JOIN Ingredient i ON i.ID = ri.Ingredient_ID
WHERE i.ID = ing_id; 
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_orders_in_range
DROP PROCEDURE IF EXISTS `fetch_orders_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_orders_in_range`(IN startDate DATE, IN endDate DATE, IN keyWord VARCHAR(52))
BEGIN
		SELECT
			o.ID as order_id, Customer_ID, CustomerName, DatePlaced, PickupTime, Fulfilled, o.Comment
		FROM `Order` o
		JOIN Customer c ON c.ID = o.Customer_ID
        WHERE DatePlaced >= startDate AND DatePlaced <= endDate AND (CustomerName LIKE CONCAT('%', keyWord, '%') OR o.Comment LIKE CONCAT('%',keyWord,'%'))
        ORDER BY DatePlaced DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_order_profit
DROP PROCEDURE IF EXISTS `fetch_order_profit`;
DELIMITER //
CREATE PROCEDURE `fetch_order_profit`()
BEGIN
	SELECT 
		IFNULL(sum(Quantity * (PriceAtSale - FoodCostAtSale)),0) as profit,
		IFNULL(sum(Quantity * PriceAtSale),0) as revenue 
	FROM `Order` o
    JOIN Order_Details od ON od.order_id = o.ID;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_order_profit_in_range
DROP PROCEDURE IF EXISTS `fetch_order_profit_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_order_profit_in_range`(IN startDate DATE, IN endDate DATE)
BEGIN
	SELECT
		DatePlaced, 
		sum(Quantity * (PriceAtSale - FoodCostAtSale)) as profit,
		sum(Quantity * PriceAtSale) as revenue 
	FROM `Order` o
    JOIN Order_Details od ON od.order_id = o.ID
    WHERE DatePlaced >= startDate AND DatePlaced <= endDate
    GROUP BY DatePlaced
    ORDER BY DatePlaced DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_order_profit_lastMonth
DROP PROCEDURE IF EXISTS `fetch_order_profit_lastMonth`;
DELIMITER //
CREATE PROCEDURE `fetch_order_profit_lastMonth`()
BEGIN
	SELECT 
		IFNULL(sum(Quantity * (PriceAtSale - FoodCostAtSale)),0) as profit,
		IFNULL(sum(Quantity * PriceAtSale),0) as revenue 
	FROM `Order` o
    JOIN Order_Details od ON od.order_id = o.ID
    WHERE YEAR(DatePlaced) <= YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
    AND MONTH(DatePlaced) <= MONTH(CURRENT_DATE - INTERVAL 1 MONTH);
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_order_quantity
DROP PROCEDURE IF EXISTS `fetch_order_quantity`;
DELIMITER //
CREATE PROCEDURE `fetch_order_quantity`()
BEGIN
		SELECT 
            SUM(StartQuantity) as quantity,
            SUM(QuantityTrashed) as trash,
            s.Date
		FROM bakery.Sales_Report s
		JOIN bakery.Sales_Report_Details spd  ON Sales_Report_ID = s.ID
        WHERE s.Date >= DATE(NOW()) + INTERVAL -14  DAY
            AND s.Date <  DATE(NOW()) + INTERVAL  0 DAY
		GROUP BY s.Date
        ORDER BY s.Date DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_products_keyword
DROP PROCEDURE IF EXISTS `fetch_products_keyword`;
DELIMITER //
CREATE PROCEDURE `fetch_products_keyword`(
	IN `keyWord` VARCHAR(52)
)
BEGIN
        SELECT *
        FROM Product p
        WHERE p.Name LIKE CONCAT('%', keyWord, '%')
          OR p.Price LIKE CONCAT('%',keyWord,'%')
          OR p.Description LIKE CONCAT('%',keyWord,'%')
          OR p.NutrientInformation LIKE CONCAT('%',keyWord,'%')
          OR p.CategoryID LIKE CONCAT('%',keyWord,'%')
        OR p.Comment LIKE CONCAT('%',keyWord,'%');
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_foodcost
DROP PROCEDURE IF EXISTS `fetch_product_foodcost`;
DELIMITER //
CREATE PROCEDURE `fetch_product_foodcost`(IN prod_id INT)
BEGIN
	SELECT 
		sum(round(((i.PricePerKG * ri.Grams) / r.ItemsProduced) / 1000, 2)) as FoodCost
	FROM Product p
		JOIN Recipe r ON r.product_id = p.ID
		JOIN Recipe_Ingredient ri ON ri.Recipe_ID = r.product_id
		JOIN Ingredient i ON i.ID = ri.Ingredient_ID
	WHERE r.product_id = prod_id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_orders
DROP PROCEDURE IF EXISTS `fetch_product_orders`;
DELIMITER //
CREATE PROCEDURE `fetch_product_orders`(IN prod_id INT)
BEGIN
SELECT
    o.ID,
    o.PickupTime,
    o.Amount,
    o.Comment,
    o.Fulfilled
		FROM bakery.`Order` o
			JOIN bakery.Order_Details od ON o.ID = od.order_id
			JOIN bakery.Product p ON od.product_id = p.ID
		WHERE p.ID = prod_id
        ORDER BY PickupTime;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_quantity
DROP PROCEDURE IF EXISTS `fetch_product_quantity`;
DELIMITER //
CREATE PROCEDURE `fetch_product_quantity`(IN prod_id INT)
BEGIN
SELECT
	p.Name as name,
	SUM(od.Quantity) as total
		FROM bakery.Product p
			JOIN bakery.Order_Details od ON p.ID = od.product_id
            JOIN bakery.`Order` o ON o.ID = od.order_id
		WHERE p.ID = prod_id
		GROUP BY
			p.Name;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_quantity_Keyword
DROP PROCEDURE IF EXISTS `fetch_product_quantity_Keyword`;
DELIMITER //
CREATE PROCEDURE `fetch_product_quantity_Keyword`(IN keyWord VARCHAR(52))
BEGIN
SELECT 
    p.ID,
	p.Name as name,
	SUM(od.Quantity) as total
		FROM bakery.Product p
			JOIN bakery.Order_Details od ON p.ID = od.product_id
            JOIN bakery.`Order` o ON o.ID = od.order_id
		WHERE p.Name LIKE CONCAT('%', keyWord, '%') 
            OR p.Comment LIKE CONCAT('%',keyWord,'%')
		GROUP BY
			p.Name,p.ID;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_quantity_sale
DROP PROCEDURE IF EXISTS `fetch_product_quantity_sale`;
DELIMITER //
CREATE PROCEDURE `fetch_product_quantity_sale`(IN prod_id INT)
BEGIN
SELECT
	p.Name as name,
	SUM(spd.QuantitySold) as total
		FROM bakery.Product p
			JOIN Sales_Report_Details spd ON spd.product_id= p.ID
		WHERE p.ID = prod_id
		GROUP BY
			p.Name;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_recipe
DROP PROCEDURE IF EXISTS `fetch_product_recipe`;
DELIMITER //
CREATE PROCEDURE `fetch_product_recipe`(IN prod_id INT)
BEGIN
	SELECT i.ID, i.Name, i.PricePerKG, ri.grams
    FROM Recipe r
    JOIN Recipe_Ingredient ri ON ri.Recipe_ID = r.product_id
    JOIN Ingredient i ON i.ID = ri.Ingredient_ID
    WHERE r.product_id = prod_id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_product_top_customer
DROP PROCEDURE IF EXISTS `fetch_product_top_customer`;
DELIMITER //
CREATE PROCEDURE `fetch_product_top_customer`(IN prod_id INT)
BEGIN
SELECT
	c.CustomerName,
	SUM(od.Quantity) as total
		FROM bakery.Customer c
			JOIN bakery.`Order` o ON c.ID = o.Customer_ID
			JOIN bakery.Order_Details od ON o.ID = od.order_id
			JOIN bakery.Product p ON od.product_id = p.ID
		WHERE p.ID = prod_id
		GROUP BY
			c.CustomerName
		ORDER BY total DESC
        limit 7;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sales_in_range
DROP PROCEDURE IF EXISTS `fetch_sales_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_sales_in_range`(
	IN `startDate` DATE,
	IN `endDate` DATE
)
BEGIN
        SELECT 
            s.ID as report_id, 
            s.DatePlaced,
            s.Comment,
            s.Hours, 
            sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale) as profit, 
            sum(QuantitySold * PriceAtSale) as revenue
		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON Sales_Report_ID = s.ID
		JOIN Product p ON product_id = p.ID
        WHERE DatePlaced >= startDate AND DatePlaced <= endDate
		GROUP BY report_id, DatePlaced
		ORDER BY DatePlaced DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sales_quantity
DROP PROCEDURE IF EXISTS `fetch_sales_quantity`;
DELIMITER //
CREATE PROCEDURE `fetch_sales_quantity`()
BEGIN
		SELECT 
           SUM(QuantitySold) as salesQuantity
		FROM Sales_Report_Details spd;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sales_two_weeks
DROP PROCEDURE IF EXISTS `fetch_sales_two_weeks`;
DELIMITER //
CREATE PROCEDURE `fetch_sales_two_weeks`()
BEGIN
		SELECT 
            SUM(StartQuantity) as quantity,
            SUM(QuantityTrashed) as trash,
            s.Date
		FROM bakery.Sales_Report s
		JOIN bakery.Sales_Report_Details spd  ON Sales_Report_ID = s.ID
        WHERE s.Date >= DATE(NOW()) + INTERVAL -14  DAY
            AND s.Date <  DATE(NOW()) + INTERVAL  0 DAY
		GROUP BY s.Date
        ORDER BY s.Date DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sale_profit
DROP PROCEDURE IF EXISTS `fetch_sale_profit`;
DELIMITER //
CREATE PROCEDURE `fetch_sale_profit`()
BEGIN
		SELECT 
            IFNULL(sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale),0) as profit, 
            IFNULL(sum(QuantitySold * PriceAtSale),0) as revenue
		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON Sales_Report_ID = s.ID;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sale_profit_in_range
DROP PROCEDURE IF EXISTS `fetch_sale_profit_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_sale_profit_in_range`(IN startDate DATE, IN endDate DATE)
BEGIN
		SELECT 
            s.ID as report_id, 
            s.Date,
            sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale) as profit, 
            sum(QuantitySold * PriceAtSale) as revenue

		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON spd.Sales_Report_ID = s.ID
        WHERE s.Date >= startDate AND s.Date <= endDate
		GROUP BY report_id, Date
		ORDER BY Date DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_sale_profit_lastMonth
DROP PROCEDURE IF EXISTS `fetch_sale_profit_lastMonth`;
DELIMITER //
CREATE PROCEDURE `fetch_sale_profit_lastMonth`()
BEGIN
		SELECT 
            IFNULL(sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale),0) as profit, 
            IFNULL(sum(QuantitySold * PriceAtSale),0) as revenue
		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON Sales_Report_ID = s.ID
        WHERE YEAR(Date) <= YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
        AND MONTH(Date) <= MONTH(CURRENT_DATE - INTERVAL 1 MONTH);
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_select_orders_in_range
DROP PROCEDURE IF EXISTS `fetch_select_orders_in_range`;
DELIMITER //
CREATE PROCEDURE `fetch_select_orders_in_range`(IN bool INT, IN startDate DATE, in endDate DATE)
BEGIN
		SELECT
			o.ID as order_id, Customer_ID, Username, DatePlaced, PickupTime, Fulfilled, o.Comment
		FROM `Order` o
		JOIN Customer c ON c.ID = o.Customer_ID
        WHERE Fulfilled = bool AND DatePlaced >= startDate AND DatePlaced <= endDate
		ORDER BY PickupTime;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_order
DROP PROCEDURE IF EXISTS `fetch_single_order`;
DELIMITER //
CREATE PROCEDURE `fetch_single_order`(
	IN `id` INT
)
BEGIN
    SELECT
        o.ID as order_id, Customer_ID, CustomerName, DatePlaced, PickupTime, Fulfilled, o.Comment, o.Amount, 
        CONCAT(c.addressOne," ",c.addressTwo) AS location, o.Status
    FROM `Order` o
    JOIN Customer c ON c.ID = o.Customer_ID
    WHERE o.ID = id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_order_detail
DROP PROCEDURE IF EXISTS `fetch_single_order_detail`;
DELIMITER //
CREATE PROCEDURE `fetch_single_order_detail`(IN o_id INT, IN p_id INT)
BEGIN
    SELECT
        product_id, Name, PriceAtSale, Quantity, od.Comment , (PriceAtSale * Quantity) AS Total
    FROM Order_Details od
    JOIN `Order` o ON o.ID = od.order_id
    JOIN Product p ON p.ID = od.product_id
    WHERE order_id = o_id AND product_id = p_id; 
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_order_details
DROP PROCEDURE IF EXISTS `fetch_single_order_details`;
DELIMITER //
CREATE PROCEDURE `fetch_single_order_details`(
	IN `id` INT
)
BEGIN
    SELECT
        od.product_id,
        p.Name,
        p.Price,
        od.Quantity,
        od.Comment,
        p.ID AS product_id,
        p.FoodCost,
        p.TimeCost,
        p.Comment ,
        p.Description,
        p.NutrientInformation,
        p.Warnings,
        p.CategoryID
    FROM Order_Details od
    JOIN `Order` o ON o.ID = od.order_id
    JOIN Product p ON p.ID = od.product_id
    WHERE o.ID = order_id
    ORDER BY p.Name;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_order_payments
DROP PROCEDURE IF EXISTS `fetch_single_order_payments`;
DELIMITER //
CREATE PROCEDURE `fetch_single_order_payments`(
	IN `id` INT
)
BEGIN
    SELECT
        pt.Payment_Type_ID AS payment_type_id,
        pt.Type,
        pm.Amount
    FROM Payment pm
    JOIN `Order` o ON o.ID = pm.order_id
    JOIN Payment_Type pt ON pt.Payment_Type_ID = pm.Payment_Type_ID
    WHERE o.ID = order_id
    ORDER BY pt.Type;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_payment
DROP PROCEDURE IF EXISTS `fetch_single_payment`;
DELIMITER //
CREATE PROCEDURE `fetch_single_payment`(IN id INT)
BEGIN
    SELECT
        order_id, Amount, Type
    FROM payment p
    JOIN Payment_Type pt ON pt.ID = p.Payment_Type_ID 
    WHERE order_id = id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_product
DROP PROCEDURE IF EXISTS `fetch_single_product`;
DELIMITER //
CREATE PROCEDURE `fetch_single_product`(IN id INT)
BEGIN
    SELECT *
    FROM Product p
    WHERE p.ID = id;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_sale
DROP PROCEDURE IF EXISTS `fetch_single_sale`;
DELIMITER //
CREATE PROCEDURE `fetch_single_sale`(
	IN `id` INT
)
BEGIN
     SELECT 
            s.ID as report_id, 
            s.DatePlaced,
            s.Comment,
            s.Hours, 
            sum(QuantitySold * ( PriceAtSale - FoodCostAtSale) - QuantityTrashed * FoodCostAtSale) as profit, 
            sum(QuantitySold * PriceAtSale) as revenue,
            sum(QuantityTrashed * FoodCostAtSale) as lost
		FROM Sales_Report s
		JOIN Sales_Report_Details spd  ON Sales_Report_ID = s.ID
		JOIN Product p ON product_id = p.ID
        WHERE s.ID = id
		GROUP BY report_id, DatePlaced
		ORDER BY DatePlaced DESC;
END//
DELIMITER ;

-- Dumping structure for procedure bakery.fetch_single_sale_details
DROP PROCEDURE IF EXISTS `fetch_single_sale_details`;
DELIMITER //
CREATE PROCEDURE `fetch_single_sale_details`(
	IN `id` INT
)
BEGIN
    SELECT
        spd.product_id,
        p.Name,
        spd.PriceAtSale,
        spd.StartQuantity,
        spd.QuantitySold,
        spd.QuantityTrashed,
        spd.FoodCostAtSale,
        spd.QuantitySold * (spd.PriceAtSale - spd.FoodCostAtSale) - spd.QuantityTrashed * spd.FoodCostAtSale AS profit,
        spd.QuantitySold * spd.PriceAtSale AS revenue,
        spd.QuantityTrashed * spd.FoodCostAtSale AS lost
    FROM
        Sales_Report_Details spd
        JOIN Sales_Report s ON s.ID = spd.Sales_Report_ID
        JOIN Product p ON p.ID = spd.product_id
    WHERE
        s.ID = id
    ORDER BY
        spd.QuantitySold DESC;
END//
DELIMITER ;

-- Dumping structure for table bakery.ingredient
DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  `PricePerKG` decimal(8,2) DEFAULT NULL,
  `Note` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Customer_ID` int unsigned NOT NULL,
  `DatePlaced` datetime NOT NULL,
  `PickupTime` datetime NOT NULL,
  `Fulfilled` int NOT NULL,
  `Comment` varchar(2048) DEFAULT NULL,
  `Amount` decimal(8,2) NOT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Customer_ID` (`Customer_ID`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.order_details
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details` (
  `order_id` int unsigned NOT NULL,
  `product_id` int unsigned NOT NULL,
  `PriceAtSale` decimal(6,2) unsigned NOT NULL,
  `FoodCostAtSale` decimal(6,2) unsigned NOT NULL,
  `Quantity` int NOT NULL,
  `Comment` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.payment
DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `order_id` int unsigned NOT NULL,
  `Payment_Type_ID` int unsigned NOT NULL,
  `Amount` double NOT NULL DEFAULT (0),
  PRIMARY KEY (`order_id`,`Payment_Type_ID`),
  KEY `Payment_Type_ID` (`Payment_Type_ID`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`Payment_Type_ID`) REFERENCES `payment_type` (`Payment_Type_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.payment_type
DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE IF NOT EXISTS `payment_type` (
  `Payment_Type_ID` int unsigned NOT NULL AUTO_INCREMENT,
  `Type` varchar(16) NOT NULL,
  PRIMARY KEY (`Payment_Type_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `productID` int unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Price` decimal(6,2) NOT NULL,
  `FoodCost` decimal(6,2) DEFAULT NULL,
  `TimeCost` int DEFAULT NULL,
  `Comment` varchar(1024) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `NutrientInformation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Warnings` varchar(50) DEFAULT NULL,
  `CategoryID` int DEFAULT NULL,
  PRIMARY KEY (`productID`) USING BTREE,
  KEY `fk_Product_Category` (`CategoryID`),
  CONSTRAINT `fk_Product_Category` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.recipe
DROP TABLE IF EXISTS `recipe`;
CREATE TABLE IF NOT EXISTS `recipe` (
  `product_id` int unsigned NOT NULL,
  `Comment` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `recipe_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.recipe_ingredient
DROP TABLE IF EXISTS `recipe_ingredient`;
CREATE TABLE IF NOT EXISTS `recipe_ingredient` (
  `Recipe_ID` int unsigned NOT NULL,
  `Ingredient_ID` int unsigned NOT NULL,
  `Grams` int unsigned NOT NULL,
  PRIMARY KEY (`Recipe_ID`,`Ingredient_ID`),
  KEY `Ingredient_ID` (`Ingredient_ID`),
  CONSTRAINT `recipe_ingredient_ibfk_1` FOREIGN KEY (`Recipe_ID`) REFERENCES `recipe` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `recipe_ingredient_ibfk_2` FOREIGN KEY (`Ingredient_ID`) REFERENCES `ingredient` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.sales_report
DROP TABLE IF EXISTS `sales_report`;
CREATE TABLE IF NOT EXISTS `sales_report` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `DatePlaced` date NOT NULL,
  `Hours` int DEFAULT NULL,
  `Comment` varchar(8048) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `Date` (`DatePlaced`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.sales_report_details
DROP TABLE IF EXISTS `sales_report_details`;
CREATE TABLE IF NOT EXISTS `sales_report_details` (
  `Sales_Report_ID` int unsigned NOT NULL,
  `product_id` int unsigned NOT NULL,
  `StartQuantity` int NOT NULL,
  `QuantitySold` int NOT NULL,
  `QuantityTrashed` int NOT NULL,
  `PriceAtSale` double NOT NULL DEFAULT (0),
  `FoodCostAtSale` double NOT NULL DEFAULT (0),
  `profit` double DEFAULT NULL,
  `revenue` double DEFAULT NULL,
  `lost` double DEFAULT NULL,
  PRIMARY KEY (`Sales_Report_ID`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `sales_report_details_ibfk_1` FOREIGN KEY (`Sales_Report_ID`) REFERENCES `sales_report` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sales_report_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.shoppingcart
DROP TABLE IF EXISTS `shoppingcart`;
CREATE TABLE IF NOT EXISTS `shoppingcart` (
  `cartID` int unsigned NOT NULL AUTO_INCREMENT,
  `totalAmount` double DEFAULT NULL,
  PRIMARY KEY (`cartID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table bakery.shoppingcartproduct
DROP TABLE IF EXISTS `shoppingcartproduct`;
CREATE TABLE IF NOT EXISTS `shoppingcartproduct` (
  `cartID` int unsigned NOT NULL,
  `productID` int unsigned NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`cartID`,`productID`) USING BTREE,
  KEY `fk_shoppingcartproduct_product` (`productID`) USING BTREE,
  CONSTRAINT `fk_shoppingcartproduct_cart` FOREIGN KEY (`cartID`) REFERENCES `shoppingcart` (`cartID`),
  CONSTRAINT `fk_shoppingcartproduct_product` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
