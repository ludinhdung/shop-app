CREATE TABLE orders
(
    id           INT AUTO_INCREMENT NOT NULL,
    user_id      INT,
    price        FLOAT              NOT NULL CHECK ( price >= 0 ),
    full_name    VARCHAR(100) DEFAULT '',
    email        VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20)        NOT NULL,
    address      VARCHAR(200)       NOT NULL,
    note         VARCHAR(100) DEFAULT '',
    order_date   datetime     DEFAULT CURRENT_TIMESTAMP,
    status       VARCHAR(20),
    total_money  FLOAT CHECK ( total_money >= 0 ),
    CONSTRAINT pk_products PRIMARY KEY (id)
);
