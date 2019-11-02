CREATE TABLE `users`
(
    `id`         int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`       varchar(100)     NOT NULL DEFAULT '',
    `email`      varchar(255)     NOT NULL DEFAULT '',
    `password`   varchar(255)              DEFAULT NULL,
    `created_at` timestamp        NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp        NULL     DEFAULT NULL ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;