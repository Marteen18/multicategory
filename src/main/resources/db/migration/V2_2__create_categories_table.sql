CREATE TABLE `categories`
(
    `id`         int(11) unsigned    NOT NULL AUTO_INCREMENT,
    `title`      varchar(100)        NOT NULL DEFAULT '',
    `parent_id`  int(10) unsigned             DEFAULT NULL,
    `is_public`  tinyint(1) unsigned NOT NULL DEFAULT 0,
    `depth`      tinyint(1) unsigned NOT NULL DEFAULT 0,
    `created_at` timestamp           NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp           NULL     DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `parent` (`parent_id`),
    CONSTRAINT `parent` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE
) DEFAULT CHARSET = utf8mb4;