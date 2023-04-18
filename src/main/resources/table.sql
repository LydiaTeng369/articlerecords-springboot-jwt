CREATE TABLE `article` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `title` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                           `department` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL,
                           `user_id` int(11) DEFAULT NULL,
                           `author` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                           `create_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                         `name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                         `password` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                         `username` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;