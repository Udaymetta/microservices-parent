CREATE DATABASE order;

CREATE TABLE order_header (
  orderHeaderId bigint NOT NULL AUTO_INCREMENT,
  orderNumber varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  customerName varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  phoneNumber varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  OrderDate datetime DEFAULT NULL,
  subTotal decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (orderHeaderId,orderNumber)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

CREATE TABLE order_detail (
  orderDetailId bigint NOT NULL AUTO_INCREMENT,
  orderHeaderId bigint NOT NULL,
  itemId int NOT NULL,
  quantity int NOT NULL,
  price decimal(10,2) NOT NULL,
  PRIMARY KEY (orderDetailId),
  KEY ibfk_1_idx (orderHeaderId),
  CONSTRAINT ibfk_1 FOREIGN KEY (orderHeaderId) REFERENCES order_header (orderHeaderId)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1 COLLATE=latin1_bin;

