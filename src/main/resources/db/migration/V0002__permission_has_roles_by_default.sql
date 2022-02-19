CREATE TABLE `permission_has_roles_by_default` (
  `permissionId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`permissionId`,`roleId`),
  KEY `FK5hat5sy7nv2ucbvn9ld8fejp7` (`roleId`),
  CONSTRAINT `FK5hat5sy7nv2ucbvn9ld8fejp7` FOREIGN KEY (`roleId`) REFERENCES `roles` (`roleId`),
  CONSTRAINT `FKac0cp9six0d5ymv7phg2m038b` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT INTO permission_has_roles_by_default(permissionId, roleId)
       VALUES (1, 1),
              (1, 2),
              (1, 3),
              (1, 4),
              (1, 5),
              (1, 6),
              
              (2, 1),
              (2, 2),
              (2, 3),
              (2, 5),              
              
              (3, 1),
              (3, 5);