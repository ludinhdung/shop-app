CREATE TABLE categories
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)       NOT NULL DEFAULT '',
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE products
(
    id            INT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(350)       NULL,
    price         FLOAT              NOT NULL CHECK ( price >= 0 ),
    thumbnail     VARCHAR(300) DEFAULT '',
    `description` LONGTEXT,
    created_at    datetime,
    updated_at    datetime,
    category_id   INT,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);