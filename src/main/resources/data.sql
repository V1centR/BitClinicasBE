CREATE TABLE IF NOT EXISTS `clinicadb`.`funcaoclinica` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomefuncao` varchar(99) NOT NULL,
  `level` int NOT NULL,
  `descricao` varchar(199) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO clinicadb.funcaoclinica (id, nomefuncao, level, descricao) VALUES
(1, 'Admin', 1, 'Administrador de clinica'),
(2, 'Médico', 2, 'Profissional médico.'),
(3, 'Atendente ADM', 3, 'Atendente de recepção telefonista com permissões especiais.'),
(4, 'Atendente', 4, 'Atendente de recepção telefonista'),
(5, 'Enfermaria', 5, 'Profissional de enfermagem'),
(6, 'Farmácia', 6, 'Profissional de farmácia.');


CREATE TABLE IF NOT EXISTS `clinicadb`.`tipoconsulta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomeconsulta` varchar(99) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `clinicadb`.`tipoconsulta` (`id`,`nomeconsulta`)
VALUES (1,'Consulta'),
(2, 'Exame'),
(3, 'Procedimento'),
(4, 'Retorno'),
(5, 'Cirurgia');

CREATE TABLE IF NOT EXISTS `clinicadb`.`convenios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomeconvenio` varchar(99) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `clinicadb`.`convenios` (`id`,`nomeconvenio`)
VALUES (1,'Amil'),
(2, 'Bradesco'),
(3, 'Intermedica'),
(4, 'SulAmérica'),
(5, 'Unimed'),
(6, 'Notredame'),
(13, 'Particular'),
(14, 'GoldenCross'),
(15, 'Porto Seguro Saúde'),
(16, 'Prevent Senior'),
(17, 'Hapvida');