CREATE TABLE `students`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `username`   varchar(15) DEFAULT NULL,
    `password`   varchar(15) DEFAULT NULL,
    `first_name` varchar(128),
    `last_name`  varchar(128),
    `email`      varchar(128),
    `version`    BIGINT,
    `create_date`  timestamp,
    `update_datee` timestamp,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
