ALTER TABLE orders ADD COLUMN `shipping_method` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `shipping_address` VARCHAR(200);
ALTER TABLE orders ADD COLUMN `shipping_date` DATE;
ALTER TABLE orders ADD COLUMN `tracking_number` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `payment_method` VARCHAR(100);
ALTER TABLE orders ADD COLUMN active TINYINT(1);
ALTER TABLE orders
    MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled');