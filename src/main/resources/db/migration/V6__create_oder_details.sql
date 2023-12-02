CREATE TABLE order_details
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    order_id           INT,
    product_id         INT,
    price              FLOAT CHECK (price >= 0),
    number_of_products INT CHECK (number_of_products > 0),
    total_money        FLOAT CHECK (total_money >= 0),
    color              VARCHAR(20) DEFAULT ''
);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_details
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);