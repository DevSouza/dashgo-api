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
  `name` varchar(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `permissions` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`permissionId`)
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

CREATE TABLE `user_has_roles` (
  `userId` bigint(20) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK5hyt5sy7nv2ucbvn9ld8fejp7` (`roleId`),
  CONSTRAINT `FK5hyt5sy7nv2ucbvn9ld8fejp7` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`),
  CONSTRAINT `FKic0cp9six0d5ymv7phg2m038b` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_has_permissions` (
  `userId` bigint(20) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`permissionId`),
  KEY `FKoq7ppt3i0u08d847w7pvm0knq` (`permissionId`),
  CONSTRAINT `FKf535ehwryk5duifqgxdiy0513` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKoq7ppt3i0u08d847w7pvm0knq` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `permission_has_roles_by_default` (
  `permissionId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`permissionId`,`roleId`),
  KEY `FK5hat5sy7nv2ucbvn9ld8fejp7` (`roleId`),
  CONSTRAINT `FK5hat5sy7nv2ucbvn9ld8fejp7` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`),
  CONSTRAINT `FKac0cp9six0d5ymv7phg2m038b` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO users (userId, email, password, username, createdAt)
	VALUES	(1, 'admin@gmail.com', '$2a$10$Z/rvwnu2aYUJKP.mKwycDeLYtWKtrcA29ZBTDBJQ2ti.n5/7xdWcG', 'admin', now());

INSERT INTO permissions (permissionId,  name, description)
       VALUES (1, 'Administrator', 'Administrador do Sistema'),
              (2, 'User', 'Usuário padrão do sistema'),
              (3, 'Editor', 'Gerador de conteudo');

INSERT INTO roles (roleId, name, description)
       VALUES (1, 'users.list', 'Listagem de usuários'),
              (2, 'users.create', 'Criar usuário'),
              (3, 'users.update', 'Atualizar dados do usuário'),
              (4, 'users.delete', 'Remover usuário'),
              (5, 'users.update.permissions', 'Atualizar Permissões do usuário'),
              (6, 'metrics.now', 'Graficos de hoje'),
              (7, 'metrics.all', 'Graficos de temporal'),
              (8, 'posts.list', 'Listagem de posts'),
              (9, 'posts.create', 'Criar posts'),
              (10, 'posts.update', 'Atualizar posts'),
              (11, 'posts.delete', 'Remover posts');

INSERT INTO permission_has_roles_by_default(permissionId, roleId) SELECT 1 AS permissionId, roleId FROM roles;
INSERT INTO permission_has_roles_by_default(permissionId, roleId) SELECT 2 AS permissionId, roleId FROM roles WHERE roleId IN (1, 2, 6, 7);
INSERT INTO permission_has_roles_by_default(permissionId, roleId) SELECT 3 AS permissionId, roleId FROM roles WHERE roleId IN (6, 8, 9, 10, 11);
INSERT INTO user_has_permissions(userId, permissionId) VALUES (1, 1);
INSERT INTO user_has_roles(userId, roleId) SELECT 1 AS userId, roleId FROM permission_has_roles_by_default where permissionId = 1;