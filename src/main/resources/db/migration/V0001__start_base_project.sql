CREATE TABLE `users` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `createdAt` DATETIME NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `roles` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_has_roles` (
  `userId` bigint(20) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK5hyt5sy7nv2ucbvn9ld8fejp7` (`roleId`),
  CONSTRAINT `FK5hyt5sy7nv2ucbvn9ld8fejp7` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`),
  CONSTRAINT `FKic0cp9six0d5ymv7phg2m038b` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `permissions` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_has_permissions` (
  `userId` bigint(20) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`permissionId`),
  KEY `FKoq7ppt3i0u08d847w7pvm0knq` (`permissionId`),
  CONSTRAINT `FKf535ehwryk5duifqgxdiy0513` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKoq7ppt3i0u08d847w7pvm0knq` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `refreshtoken` (
  `refreshTokenId` bigint(20) NOT NULL AUTO_INCREMENT,
  `expiryDate` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`refreshTokenId`),
  UNIQUE KEY `UK_or156wbneyk8noo4jstv55ii3` (`token`),
  KEY `FK7vjg9lbr08c65dfvqd69y9dm0` (`userId`),
  CONSTRAINT `FK7vjg9lbr08c65dfvqd69y9dm0` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

INSERT INTO users (userId, email, password, username, createdAt)
	VALUES	(1, 'admin@gmail.com', '$2a$10$Z/rvwnu2aYUJKP.mKwycDeLYtWKtrcA29ZBTDBJQ2ti.n5/7xdWcG', 'admin', now()),
		(2, 'user@gmail.com', '$2a$10$Z/rvwnu2aYUJKP.mKwycDeLYtWKtrcA29ZBTDBJQ2ti.n5/7xdWcG', 'user', now()),
		(3, 'editor@gmail.com', '$2a$10$Z/rvwnu2aYUJKP.mKwycDeLYtWKtrcA29ZBTDBJQ2ti.n5/7xdWcG', 'editor', now());

INSERT INTO roles (roleId,  name)
       VALUES     (1,       'users.list'),
                  (2,       'users.create'),
                  (3,       'users.update'),
                  (4,       'users.delete'),
                  (5,       'metrics.now'),
                  (6,       'metrics.all');


INSERT INTO user_has_roles(userId, roleId)
       VALUES (1, 1),
              (1, 2),
              (1, 3),
              (1, 4),
              (1, 5),
              (1, 6),
              
              (2, 1),
              (2, 2),
              (2, 3),              
              
              (3, 1),
              (3, 5);


INSERT INTO permissions (permissionId,  name)
       VALUES     (1,       'administrator'),
                  (2,       'user'),
                  (3,       'editor');

INSERT INTO user_has_permissions(userId, permissionId)
       VALUES (1, 1),
              (2, 2),
              (3, 3);