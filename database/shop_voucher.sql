CREATE TABLE `user`(
                       `user_id` BIGINT NOT NULL,
                       `name` VARCHAR(255) NOT NULL,
                       PRIMARY KEY(`user_id`)
);
CREATE TABLE `shop`(
                       `shop_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       `shop_name`  VARCHAR(255) NOT NULL
);
CREATE TABLE `voucher`(
                          `voucher_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          `voucher_name` VARCHAR(255) NOT NULL,
                          `voucher_value` VARCHAR(255) NOT NULL
);
CREATE TABLE `shop_voucher`(
                               `shop_voucher_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                               `shop_id` BIGINT NOT NULL,
                               `voucher_id` BIGINT NOT NULL
);
CREATE TABLE `user_shop_subscription`(
                                         `user_shop_subscription_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                         `user_id` BIGINT NOT NULL,
                                         `shop_id` BIGINT NOT NULL
);


INSERT INTO `user` (`user_id`, `name`)
VALUES
    (1, 'Alice'),
    (2, 'Bob'),
    (3, 'Charlie'),
    (4, 'Diana');

INSERT INTO `shop` (`shop_id`, `shop_name`)
VALUES
    (1, 'Tech Store'),
    (2, 'Book World'),
    (3, 'Fashion Hub');

INSERT INTO `voucher` (`voucher_id`, `voucher_name`, `voucher_value`)
VALUES
    (1, 'voucher001', '10% OFF'),
    (2, 'voucher002', '20% OFF'),
    (3, 'voucher003', 'Free Shipping');

INSERT INTO `shop_voucher` (`shop_voucher_id`, `shop_id`, `voucher_id`)
VALUES
    (1, 1, 1), -- Tech Store offers voucher001
    (2, 1, 3), -- Tech Store offers voucher003
    (3, 2, 2), -- Book World offers voucher002
    (4, 3, 1); -- Fashion Hub offers voucher001

INSERT INTO `user_shop_subscription` (`user_shop_subscription_id`, `user_id`, `shop_id`)
VALUES
    (1, 1, 1), -- Alice follows Tech Store
    (2, 1, 2), -- Alice follows Book World
    (3, 2, 1), -- Bob follows Tech Store
    (4, 3, 3), -- Charlie follows Fashion Hub
    (5, 4, 2); -- Diana follows Book World

# ALTER TABLE
    #     `shop_voucher` ADD CONSTRAINT `shop_voucher_voucher_id_foreign` FOREIGN KEY(`voucher_id`) REFERENCES `voucher`(`voucher_id`);
# ALTER TABLE
    #     `shop_voucher` ADD CONSTRAINT `shop_voucher_shop_id_foreign` FOREIGN KEY(`shop_id`) REFERENCES `shop`(`shop_id`);
# ALTER TABLE
    #     `user_shop_subscription` ADD CONSTRAINT `user_shop_subscription_shop_id_foreign` FOREIGN KEY(`shop_id`) REFERENCES `shop`(`shop_id`);
# ALTER TABLE
    #     `user_shop_subscription` ADD CONSTRAINT `user_shop_subscription_user_id_foreign` FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`);
