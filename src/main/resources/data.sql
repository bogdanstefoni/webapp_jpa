CREATE TABLE `students`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `username`   varchar(15) DEFAULT NULL,
    `password`   varchar(15) DEFAULT NULL,
    `first_name` varchar(128) NOT NULL,
    `last_name`  varchar(128) NOT NULL,
    `email`      varchar(128) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
