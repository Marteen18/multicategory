CREATE TABLE `goods`
(
    `id`          int(11) unsigned NOT NULL AUTO_INCREMENT,
    `user_id`     int(10) unsigned                               DEFAULT NULL,
    `category_id` int(10) unsigned                               DEFAULT NULL,
    `title`       varchar(255)     NOT NULL                      DEFAULT '',
    `description` text                                           DEFAULT NULL,
    `price`       decimal(8, 2)    NOT NULL                      DEFAULT 0.00,
    `details`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`details`)),
    `created_at`  timestamp        NOT NULL                      DEFAULT current_timestamp(),
    `updated_at`  timestamp        NULL                          DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `category_id` (`category_id`),
    CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) DEFAULT CHARSET = utf8mb4;