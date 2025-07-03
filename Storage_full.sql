CREATE DATABASE IF NOT EXISTS tool_storage_db;
USE tool_storage_db;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `id_employee` varchar(8) NOT NULL,
  `id_position` varchar(8) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `patronymic` varchar(30) DEFAULT NULL,
  `phone_number` varchar(18) NOT NULL,
  `email` varchar(255) NOT NULL,
  `login` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id_employee`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `FK_positions_e_idx` (`id_position`),
  CONSTRAINT `FK_position_e` FOREIGN KEY (`id_position`) REFERENCES `positions` (`id_position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
INSERT INTO `employees` VALUES ('EMP0','PST0','Карпов','Дмитрий','Николаевич','8(904)728-82-84','karpov92@gmail.com','karpov_nikolai','$2a$10$daQNq9B18ybz0QTw6xC7C.y2hu6NkCD1nwDMuGeWddpC2hItceddm'),
                               ('EMP1','PST1','Гаврилов','Андрей','Ибрагимович','8(910)718-64-08 ','gavr125@gmail.com','gavrilov_andrey','$2a$10$kM65mahGxVdKQfG8aro10.Qcf8uTlU40KnWCD.bbN662bdGml0liO'),
                               ('EMP10','PST7','Гончаров','Николай','Георгиевич','8(904)777-71-99','goncharov21@gmail.com','goncharov_nikolay','$2a$10$7iiejrhayWRPNhvQqF3iZesArVkdGMx2mSLhYx.8OTdXdAaRMGX/a'),
                               ('EMP2','PST2','Сорокин','Дмитрий','Дмитриевич','8(663)051-98-77','63sorok@gmail.com','sorokin_dmitriy','$2a$10$k4QM9XzTFCncqAtbyTmWT./sBEILfFqkpFJvSx3Nk63J5.eizPUnK'),
                               ('EMP3','PST3','Шубин','Захар','Маркович','8(352)338-08-88','shubin99@mail.ru','shubin_zahar','$2a$10$WwG.3OitlK88s7hQdHv/h.wBWyXb3dmyOXPOvmP1zDspiKbeHcnk2'),
                               ('EMP4','PST4','Крылов','Михаил','Михайлович','8(687)972-17-18','krylov2@yandex.ru','krylov_mihail','$2a$10$4.NgLxEFnEwd22FW7Png8eV8565MkKij.WgsFztbCdAIkPfcKCUFi'),
                               ('EMP5','PST5','Жданов','Платон','Ильич','8(944)149-52-93','52zhdanov@yandex.ru','zhdanov_platon','$2a$10$2oAC82M2JQZ1f09i28Lz9e1LGW//RySg4Vi7w0UdM5VuWnWzF8Jli'),
                               ('EMP6','PST6','Логинов','Юрий','Максимович','8(911)784-75-68','1loginov9@gmail.com','loginov_yuriy','$2a$10$S.GexnAqlQ1Tmwu95bqsmeKnqAHwXt1tLLzOnzY6.alUtfgtByIbS'),
                               ('EMP7','PST7','Ершов','Александр','Владиславович','8(377)345-20-81','43ershov7@mail.ru','ershov_aleksandr','$2a$10$Xi79tnkpB1awj2oaUmd5Deu5mSNPXvt2MACe3G9BsuMenCEkUsFby'),
                               ('EMP8','PST8','Гусев','Макар','Львович','8(088)860-23-69','gusev86@mail.ru','gusev_makar','$2a$10$NkidsNLr5ZEZrD1KHIsTneMTEAqyCSmBfLCVjrdEDdY5ph3K1YUa.'),
                               ('EMP9','PST9','Мухин','Никита','Матвеевич','8(904)819-02-11','322muhin@gmail.com','muhin_nikita','$2a$10$.nvEUx1DRp/c6FQO/W5DUuSEeTA1kA3idrsgoZfS83OsMpUogdSOW');
UNLOCK TABLES;

--
-- Table structure for table `history_tool_issue`
--

DROP TABLE IF EXISTS `history_tool_issue`;
CREATE TABLE `history_tool_issue` (
  `id_employee` varchar(8) NOT NULL,
  `id_tool` varchar(8) NOT NULL,
  `action` enum('Выдан','Возврат') NOT NULL,
  `date_and_time_issue` datetime NOT NULL,
  PRIMARY KEY (`id_employee`,`date_and_time_issue`,`id_tool`),
  KEY `FK_tool_hti_idx` (`id_tool`),
  CONSTRAINT `FK_employee_hti` FOREIGN KEY (`id_employee`) REFERENCES `employees` (`id_employee`),
  CONSTRAINT `FK_tool_hti` FOREIGN KEY (`id_tool`) REFERENCES `tools` (`id_tool`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `history_tool_issue`
--

LOCK TABLES `history_tool_issue` WRITE;
INSERT INTO `history_tool_issue` VALUES ('EMP1','TEJS-1','Выдан','2025-05-14 13:20:00'),
                                        ('EMP10','TVRC-1','Выдан','2025-04-30 12:12:00'),
                                        ('EMP10','TCHL-3','Выдан','2025-05-09 15:14:00'),
                                        ('EMP2','TDRM-1','Выдан','2025-05-08 15:22:00'),
                                        ('EMP2','TDGG-2','Выдан','2025-05-17 14:54:00'),
                                        ('EMP2','TDGG-2','Возврат','2025-05-19 11:37:00'),
                                        ('EMP3','TCLM-2','Выдан','2025-05-02 14:46:00'),
                                        ('EMP3','TEJS-3','Выдан','2025-05-28 12:45:00'),
                                        ('EMP5','TMCR-2','Выдан','2025-04-29 13:52:00'),
                                        ('EMP5','TDJW-1','Выдан','2025-05-07 11:00:00'),
                                        ('EMP6','TPLR-4','Выдан','2025-05-22 14:07:00'),
                                        ('EMP6','TSSH-4','Выдан','2025-05-29 12:32:00'),
                                        ('EMP6','TWCL-1','Выдан','2025-06-10 11:27:00'),
                                        ('EMP7','TPRT-5','Выдан','2025-06-03 13:00:00'),
                                        ('EMP7','TUTN-1','Выдан','2025-06-17 12:10:00'),
                                        ('EMP7','TUTN-1','Возврат','2025-06-17 16:00:00'),
                                        ('EMP8','TRWR-2','Выдан','2025-05-30 10:52:00'),
                                        ('EMP8','TCHL-10','Выдан','2025-07-03 02:05:56'),
                                        ('EMP9','TLVS-2','Выдан','2025-05-20 10:32:00'),
                                        ('EMP9','TLVS-2','Возврат','2025-05-21 13:11:00'),
                                        ('EMP9','TMRL-1','Выдан','2025-06-02 10:27:00');
UNLOCK TABLES;

--
-- Table structure for table `history_write_off_instrument`
--

DROP TABLE IF EXISTS `history_write_off_instrument`;
CREATE TABLE `history_write_off_instrument` (
  `id_write_off` varchar(8) NOT NULL,
  `id_tool` varchar(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date_and_time_write_off` datetime NOT NULL,
  PRIMARY KEY (`id_write_off`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `history_write_off_instrument`
--

LOCK TABLES `history_write_off_instrument` WRITE;
INSERT INTO `history_write_off_instrument` VALUES ('WRF1','TMHS-2','Ножовка по металлу','2025-05-13 15:32:00'),
                                                  ('WRF10','TPRT-1','Угломер','2025-06-11 11:50:00'),
                                                  ('WRF11','TMRL-2','Линейка металлическая','2025-05-26 12:42:00'),
                                                  ('WRF12','TDGG-6','Глубиномер','2025-06-27 10:08:14'),
                                                  ('WRF13','TCHL-12','Зубило','2025-07-03 04:58:18'),
                                                  ('WRF14','TCHL-11','Зубило','2025-07-03 04:59:58'),
                                                  ('WRF15','TDGG-11','Глубиномер','2025-07-03 05:10:53'),
                                                  ('WRF16','TDGG-13','Глубиномер','2025-07-03 05:27:17'),
                                                  ('WRF2','TEJS-4','Лобзик электрический по металлу','2025-04-29 12:24:00'),
                                                  ('WRF3','TDRM-3','Сверло ручное','2025-05-07 13:03:00'),
                                                  ('WRF4','TRHM-3','Молоток резиновый','2025-05-16 16:22:00'),
                                                  ('WRF5','TSSH-1','Кувалда малогабаритная','2025-04-23 12:47:00'),
                                                  ('WRF6','TRWR-3','Вороток с трещоткой','2025-06-17 16:31:00'),
                                                  ('WRF7','TLVS-3','Тиски слесарные','2025-05-26 17:27:00'),
                                                  ('WRF8','TLVS-4','Тиски слесарные','2025-05-21 13:39:00'),
                                                  ('WRF9','TMGH-3','Магнитный держатель для мелких деталей','2025-05-01 10:46:00');
UNLOCK TABLES;

--
-- Table structure for table `positions`
--

DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id_position` varchar(8) NOT NULL,
  `title_position` varchar(80) NOT NULL,
  `requirements` text NOT NULL,
  `duties` text NOT NULL,
  `salary` decimal(9,2) NOT NULL,
  `role` enum('admin','storekeeper','worker') NOT NULL,
  PRIMARY KEY (`id_position`),
  UNIQUE KEY `title_position_UNIQUE` (`title_position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `positions`
--

LOCK TABLES `positions` WRITE;
INSERT INTO `positions` VALUES ('PST0','Администратор информационной системы','Среднее профессиональное или высшее образование программиста','Администрировать информациооную систему',80000.00,'admin'),
                               ('PST1','Инженер-технолог','Иметь высшее техническое образование, знание технологических процессов производства, умение разрабатывать и внедрять технологическую документацию, владеть профильным программным обеспечением, разбираться в нормативных документах, обладать внимательностью, аналитическим мышлением.','Разрабатывать и оптимизировать технологические процессы, подготавливать и вести техническую документацию, контролировать соблюдение технологических режимов на производстве, участвовать во внедрении нового оборудования и технологий, анализировать причины брака и разрабатывать меры по его снижению, обеспечивать соответствие продукции стандартам качества, взаимодействовать с другими отделами по вопросам производства и технической поддержки.',60000.00,'worker'),
                               ('PST2','Контроллер станочных и слесарных работ','Иметь среднее профессиональное образование по технической специальности, знать методы контроля и измерения, устройство и принцип работы станков и инструмента, основы чертежей и технической документации, уметь пользоваться измерительным инструментом, быть внимательным, ответственным и аккуратным.','Осуществлять контроль качества станочных и слесарных операций на всех этапах производства, проверять соответствие деталей чертежам и техническим условиям, проводить измерения с использованием контрольно-измерительного инструмента, оформлять документацию по результатам контроля, выявлять дефекты, принимать участие в разборе причин брака и контролировать устранение несоответствий.',50000.00,'worker'),
                               ('PST3','Оператор станков с программным управлением','Необходимо среднее профессиональное техническое образование, знание устройства и принципа работы станков с ЧПУ, умение читать технические чертежи и пользоваться измерительным инструментом, базовые навыки программирования, внимательность, ответственность, аккуратность.','Осуществлять настройку и управление станками с ЧПУ, подготавливать и загружать управляющие программы, контролировать процесс обработки деталей, выполнять корректировку режимов резания, следить за состоянием инструмента и оборудования, проводить измерения и проверку готовой продукции, обеспечивать соблюдение технических требований и норм безопасности.',65000.00,'worker'),
                               ('PST4','Старший кладовщик инструментальной кладовой','Среднее профессиональное или техническое образование, знание складского учета и документооборота, умение организовать работу кладовой и вести инвентаризацию, внимательность, ответственность, навыки работы с программами складского учета.','Организовывать прием, хранение и выдачу инструментов и расходных материалов, контролировать наличие и сохранность имущества, вести учет и документацию, проводить инвентаризации, распределять задания сотрудникам кладовой, взаимодействовать с производственными подразделениями для своевременного обеспечения инструментами, следить за порядком и соблюдением правил охраны труда и техники безопасности.',50000.00,'storekeeper'),
                               ('PST5','Наладчик станков и манипуляторов с ПУ','Среднее профессиональное техническое образование, знание принципов работы и устройства станков и манипуляторов с ЧПУ, опыт наладки, настройки и ремонта оборудования, умение читать техническую документацию и схемы, навыки программирования и диагностики, внимательность, ответственность, умение работать с измерительными приборами.','Проводить наладку, настройку и тестирование станков и манипуляторов с программным управлением, устранять технические неполадки, оптимизировать работу оборудования, контролировать корректность работы программ, выполнять техническое обслуживание и профилактику станков, взаимодействовать с оператором и другими службами для обеспечения бесперебойного производства, соблюдать нормы безопасности и производственные регламенты.',150000.00,'worker'),
                               ('PST6','Фрезеровщик','Среднее профессиональное техническое образование, знание устройства и работы фрезерных станков с ЧПУ, умение читать технические чертежи, работа с измерительными инструментами, базовые навыки программирования, внимательность, ответственность, аккуратность.','Подготавливать и настраивать фрезерные станки с ЧПУ, выполнять механическую обработку деталей по техническим чертежам, контролировать качество изделий с помощью измерительных инструментов, обслуживать оборудование, соблюдать правила техники безопасности и вести отчетность по выполненным работам.',80000.00,'worker'),
                               ('PST7','Слесарь МСР','Среднее профессиональное техническое образование, знание устройства и принципов работы слесарного инструмента и оборудования, умение читать технические чертежи, навыки сборки и разборки узлов и деталей, внимательность, аккуратность, ответственность, соблюдение правил техники безопасности.','Выполнять сборки и разборки конструкций и узлов согласно чертежам, подготавливать детали к сборке, выполнять слесарные работы, контролировать качества сборки, обслуживать и ухаживать за инструментом и оборудованием, соблюдать производственные нормы и правила безопасности.',100000.00,'worker'),
                               ('PST8','Токарь','Среднее профессиональное техническое образование, знание устройства и принципов работы токарных станков с ЧПУ и ручных станков, умение читать технические чертежи, навыки работы с измерительными инструментами, внимательность, аккуратность, ответственность, соблюдение техники безопасности.','Настраивать и обслуживать токарные станки, выполнять токарную обработку деталей согласно чертежам, контролировать качества готовых изделий с помощью измерительных приборов, проведение текущего ухода за оборудованием, соблюдать технологические процессы и правила безопасности.',80000.00,'worker'),
                               ('PST9','Шлифовщик','Среднее профессиональное техническое образование, знание устройства и принципов работы шлифовальных станков, умение читать технические чертежи, навыки работы с измерительными инструментами, внимательность, аккуратность, ответственность, соблюдение техники безопасности.','Подготавливать и настраивать шлифовальное оборудование, выполнять шлифовальную обработку деталей по технической документации, контролировать качество поверхности, уход за инструментом и оборудованием, соблюдать технологические процессы и правила техники безопасности.',80000.00,'worker');
UNLOCK TABLES;

--
-- Table structure for table `storage_locations`
--

DROP TABLE IF EXISTS `storage_locations`;
CREATE TABLE `storage_locations` (
  `id_place` varchar(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`id_place`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `storage_locations`
--

LOCK TABLES `storage_locations` WRITE;
INSERT INTO `storage_locations` VALUES ('STL1','Инструментальный склад','Помещение для хранения инструментов и оборудования, обеспечивающее их сохранность и удобный доступ для сотрудников'),
                                       ('STL2','Механический цех','Производственное помещение, оснащённое станками и оборудованием для обработки и изготовления металлических деталей'),
                                       ('STL3','Гальванический цех','Производственное помещение для нанесения защитных и декоративных покрытий на изделия с помощью гальванических и других химических процессов'),
                                       ('STL4','Шкаф № 109','Закрытое место хранения для упорядоченного размещения инструментов, материалов или документации с ограниченным доступом'),
                                       ('STL5','Шкаф № 5','Закрытое место хранения для упорядоченного размещения инструментов, материалов или документации с ограниченным доступом'),
                                       ('STL6','Шкаф № 52','Закрытое место хранения для упорядоченного размещения инструментов, материалов или документации с ограниченным доступом'),
                                       ('STL7','Шкаф № 81','Закрытое место хранения для упорядоченного размещения инструментов, материалов или документации с ограниченным доступом');
UNLOCK TABLES;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
CREATE TABLE `tools` (
  `id_tool` varchar(8) NOT NULL,
  `article_tool_type` varchar(8) NOT NULL,
  `id_place` varchar(8) NOT NULL,
  `availability` tinyint(4) NOT NULL,
  `date_and_time_admission` datetime NOT NULL,
  PRIMARY KEY (`id_tool`),
  KEY `FK_article_tool_type_t_idx` (`article_tool_type`),
  KEY `FK_place_t_idx` (`id_place`),
  CONSTRAINT `FK_article_tool_type_t` FOREIGN KEY (`article_tool_type`) REFERENCES `types_of_tools` (`article_tool_type`),
  CONSTRAINT `FK_place_t` FOREIGN KEY (`id_place`) REFERENCES `storage_locations` (`id_place`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
INSERT INTO `tools` VALUES ('TCHL-1','CHL','STL3',1,'2024-09-18 09:09:00'),
                           ('TCHL-10','CHL','STL2',0,'2025-07-03 01:53:26'),
                           ('TCHL-2','CHL','STL3',1,'2024-09-23 16:07:00'),
                           ('TCHL-3','CHL','STL7',0,'2025-05-07 13:31:00'),
                           ('TCHL-4','CHL','STL2',1,'2025-02-13 12:58:00'),
                           ('TCHL-5','CHL','STL1',1,'2025-01-29 17:44:00'),
                           ('TCHL-6','CHL','STL1',1,'2025-06-27 09:15:54'),
                           ('TCHL-7','CHL','STL1',1,'2025-06-28 17:16:25'),
                           ('TCHL-8','CHL','STL5',1,'2025-06-30 19:19:28'),
                           ('TCHL-9','CHL','STL1',1,'2025-07-02 18:32:59'),
                           ('TCLM-1','CLM','STL7',1,'2025-05-08 15:55:00'),
                           ('TCLM-2','CLM','STL7',0,'2025-04-25 11:06:00'),
                           ('TCLM-3','CLM','STL2',1,'2025-01-27 15:38:00'),
                           ('TCLM-4','CLM','STL1',1,'2025-07-03 02:38:08'),
                           ('TDGG-1','DGG','STL6',1,'2025-06-04 11:13:00'),
                           ('TDGG-10','DGG','STL5',1,'2025-06-28 17:16:25'),
                           ('TDGG-12','DGG','STL2',1,'2025-07-03 05:10:27'),
                           ('TDGG-14','DGG','STL4',1,'2025-07-03 05:26:59'),
                           ('TDGG-15','DGG','STL4',1,'2025-07-03 05:26:59'),
                           ('TDGG-2','DGG','STL4',1,'2025-01-17 09:49:00'),
                           ('TDGG-3','DGG','STL1',1,'2024-11-19 13:19:00'),
                           ('TDGG-4','DGG','STL5',1,'2025-06-27 09:19:01'),
                           ('TDGG-5','DGG','STL5',1,'2025-06-27 09:19:01'),
                           ('TDGG-7','DGG','STL5',1,'2025-06-27 09:19:01'),
                           ('TDGG-8','DGG','STL5',1,'2025-06-28 17:16:25'),
                           ('TDGG-9','DGG','STL5',1,'2025-06-28 17:16:25'),
                           ('TDJW-1','DJW','STL7',0,'2024-07-02 13:08:00'),
                           ('TDJW-2','DJW','STL7',1,'2024-09-12 11:40:00'),
                           ('TDJW-3','DJW','STL7',1,'2025-02-20 17:03:00'),
                           ('TDRM-1','DRM','STL2',0,'2024-04-24 10:31:00'),
                           ('TDRM-2','DRM','STL1',1,'2025-01-14 09:49:00'),
                           ('TDRM-4','DRM','STL7',1,'2024-08-20 16:14:00'),
                           ('TEJS-1','EJS','STL1',0,'2024-06-21 17:33:00'),
                           ('TEJS-2','EJS','STL5',1,'2025-06-17 14:27:00'),
                           ('TEJS-3','EJS','STL5',0,'2024-12-26 15:45:00'),
                           ('TEJS-5','EJS','STL1',1,'2025-05-23 16:44:00'),
                           ('TISD-1','ISD','STL5',1,'2024-06-25 17:06:00'),
                           ('TISD-2','ISD','STL4',1,'2025-04-04 15:18:00'),
                           ('TISD-3','ISD','STL3',1,'2024-06-07 09:19:00'),
                           ('TISD-4','ISD','STL6',1,'2025-05-28 10:21:00'),
                           ('TLVS-1','LVS','STL3',1,'2024-05-02 10:41:00'),
                           ('TLVS-2','LVS','STL6',1,'2024-06-26 17:59:00'),
                           ('TLVS-5','LVS','STL6',1,'2025-04-15 16:45:00'),
                           ('TMCR-1','MCR','STL1',1,'2024-07-24 10:55:00'),
                           ('TMCR-2','MCR','STL7',0,'2024-05-01 17:04:00'),
                           ('TMCR-3','MCR','STL7',1,'2025-03-13 09:56:00'),
                           ('TMCR-4','MCR','STL5',1,'2024-09-17 10:31:00'),
                           ('TMCR-5','MCR','STL1',1,'2025-02-03 15:11:00'),
                           ('TMCT-1','MCT','STL5',1,'2024-08-09 13:20:00'),
                           ('TMCT-2','MCT','STL2',1,'2024-08-07 15:42:00'),
                           ('TMCT-3','MCT','STL3',1,'2024-05-30 13:21:00'),
                           ('TMCT-4','MCT','STL1',1,'2024-11-11 09:37:00'),
                           ('TMCT-5','MCT','STL4',1,'2024-04-25 10:03:00'),
                           ('TMGH-1','MGH','STL5',1,'2024-11-12 11:51:00'),
                           ('TMGH-2','MGH','STL1',1,'2024-06-03 13:39:00'),
                           ('TMHS-1','MHS','STL1',1,'2024-08-06 12:15:00'),
                           ('TMHS-3','MHS','STL2',1,'2024-06-14 09:42:00'),
                           ('TMRL-1','MRL','STL7',0,'2025-03-04 09:42:00'),
                           ('TMRL-3','MRL','STL3',1,'2024-05-22 14:08:00'),
                           ('TMRL-4','MRL','STL6',1,'2025-06-12 13:54:00'),
                           ('TPCD-1','PCD','STL6',1,'2025-02-24 14:46:00'),
                           ('TPCD-2','PCD','STL5',1,'2025-01-01 17:15:00'),
                           ('TPCD-3','PCD','STL1',1,'2024-06-05 09:11:00'),
                           ('TPCD-4','PCD','STL7',1,'2024-10-09 10:52:00'),
                           ('TPLR-1','PLR','STL5',1,'2025-02-28 17:56:00'),
                           ('TPLR-2','PLR','STL3',1,'2025-06-05 12:40:00'),
                           ('TPLR-3','PLR','STL5',1,'2025-02-06 15:15:00'),
                           ('TPLR-4','PLR','STL2',0,'2024-11-25 16:41:00'),
                           ('TPLR-5','PLR','STL3',1,'2024-07-16 12:05:00'),
                           ('TPLR-6','PLR','STL6',1,'2025-02-04 15:06:00'),
                           ('TPRT-2','PRT','STL3',1,'2025-02-19 16:42:00'),
                           ('TPRT-3','PRT','STL1',1,'2025-01-02 17:39:00'),
                           ('TPRT-4','PRT','STL3',1,'2025-01-28 12:02:00'),
                           ('TPRT-5','PRT','STL5',0,'2025-04-24 16:56:00'),
                           ('TRHM-1','RHM','STL7',1,'2024-10-03 13:49:00'),
                           ('TRHM-2','RHM','STL3',1,'2024-07-19 16:01:00'),
                           ('TRHM-4','RHM','STL5',1,'2025-04-17 09:36:00'),
                           ('TRWR-1','RWR','STL4',1,'2025-06-06 12:18:00'),
                           ('TRWR-2','RWR','STL5',0,'2025-05-20 17:33:00'),
                           ('TRWR-4','RWR','STL2',1,'2024-05-20 13:24:00'),
                           ('TRWR-5','RWR','STL5',1,'2025-03-24 10:32:00'),
                           ('TSCL-1','SCL','STL5',1,'2024-10-01 17:15:00'),
                           ('TSCL-2','SCL','STL4',1,'2024-04-22 10:03:00'),
                           ('TSCL-3','SCL','STL5',1,'2024-04-09 12:27:00'),
                           ('TSPL-1','SPL','STL1',1,'2024-10-23 10:16:00'),
                           ('TSPL-2','SPL','STL7',1,'2025-02-21 12:47:00'),
                           ('TSPL-3','SPL','STL2',1,'2025-03-28 14:15:00'),
                           ('TSSH-2','SSH','STL4',1,'2024-05-06 15:08:00'),
                           ('TSSH-3','SSH','STL6',1,'2025-06-10 12:09:00'),
                           ('TSSH-4','SSH','STL3',0,'2024-07-01 10:57:00'),
                           ('TSSH-5','SSH','STL3',1,'2025-01-08 11:15:00'),
                           ('TTRI-1','TRI','STL4',1,'2024-06-28 11:17:00'),
                           ('TTRI-2','TRI','STL5',1,'2025-05-21 11:31:00'),
                           ('TTRI-3','TRI','STL4',1,'2024-10-30 14:09:00'),
                           ('TUTN-1','UTN','STL3',1,'2025-03-05 10:18:00'),
                           ('TUTN-2','UTN','STL1',1,'2024-08-05 13:06:00'),
                           ('TUTN-3','UTN','STL4',1,'2024-08-23 11:54:00'),
                           ('TUTN-4','UTN','STL4',1,'2024-12-24 11:11:00'),
                           ('TUTN-5','UTN','STL5',1,'2025-01-16 17:00:00'),
                           ('TVRC-1','VRC','STL4',0,'2025-01-21 10:48:00'),
                           ('TVRC-2','VRC','STL2',1,'2024-05-13 17:24:00'),
                           ('TVRC-3','VRC','STL5',1,'2024-10-29 12:55:00'),
                           ('TVRC-4','VRC','STL4',1,'2024-08-14 16:21:00'),
                           ('TWCL-1','WCL','STL4',0,'2025-05-01 16:27:00'),
                           ('TWCL-2','WCL','STL4',1,'2024-07-05 16:12:00'),
                           ('TWCL-3','WCL','STL1',1,'2024-05-31 10:13:00'),
                           ('TWCL-4','WCL','STL6',1,'2024-09-16 13:56:00');
UNLOCK TABLES;

--
-- Table structure for table `types_of_tools`
--

DROP TABLE IF EXISTS `types_of_tools`;
CREATE TABLE `types_of_tools` (
  `article_tool_type` varchar(8) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`article_tool_type`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `types_of_tools`
--

LOCK TABLES `types_of_tools` WRITE;
INSERT INTO `types_of_tools` VALUES ('CHL','Зубило','Режущий инструмент для обработки и разделки камня или металла ударами молотка'),
                                    ('CLM','Струбцины','Зажимные приспособления для крепления и фиксации деталей во время сборки и обработки'),
                                    ('DGG','Глубиномер','Инструмент для измерения глубины отверстий и пазов'),
                                    ('DJW','Разводной гаечный ключ','Ручной инструмент с регулируемой губкой для работы с гайками и болтами разных размеров'),
                                    ('DRM','Сверло ручное','Инструмент для создания отверстий вручную'),
                                    ('EJS','Лобзик электрический по металлу','Электроприбор для резки металлических листов и профилей с помощью пилки'),
                                    ('ISD','Ударная отвёртка','Инструмент для вкручивания и выкручивания винтов с ударным воздействием при вращении'),
                                    ('LVS','Тиски слесарные','Инструмент для надежного закрепления деталей при работе'),
                                    ('MCR','Микрометр','Высокоточный инструмент для измерения малых линейных размеров с большой точностью'),
                                    ('MCT','Фреза','Режущий инструмент для обработки поверхности и создания канавок на деталях'),
                                    ('MGH','Магнитный держатель для мелких деталей','Приспособление для удержания и фиксации металлических мелких деталей с помощью магнита'),
                                    ('MHS','Ножовка по металлу','Ручной инструмент для резки металлических деталей'),
                                    ('MRL','Линейка металлическая','Прямой измерительный инструмент из металла для точного нанесения размеров'),
                                    ('PCD','Отвёртка крестовая','Ручной инструмент для закручивания и выкручивания винтов с крестообразным шлицем'),
                                    ('PLR','Пассатижы','Ручной инструмент для захвата, сгибания и скручивания проволоки и мелких деталей'),
                                    ('PRT','Угломер','Инструмент для измерения и проверки углов'),
                                    ('RHM','Молоток резиновый','Инструмент с мягкой головкой для ударов без повреждения поверхности'),
                                    ('RWR','Вороток с трещоткой','Ручной инструмент для вращения насадок с возможностью быстрого изменения направления вращения'),
                                    ('SCL','Угольник с зажимом','Измерительный инструмент с фиксатором для точного контроля углов и закрепления заготовок'),
                                    ('SPL','Кусачки боковые','Ручной инструмент для резки проводов и тонких металлических деталей сбоку'),
                                    ('SSH','Кувалда малогабартиная','Тяжёлый ручной молоток для сильных ударов в ограниченном пространстве'),
                                    ('TRI','Щипцы для снятия изоляции','Инструмент для удаления изоляционного слоя с проводов'),
                                    ('UTN','Клещи универсальные','Ручной инструмент для захвата, удержания и изгиба различных предметов'),
                                    ('VRC','Штангенциркуль','Точный инструмент для измерения наружных и внутренних размеров, а также глубины'),
                                    ('WCL','Зажимы для сварки','Инструменты для надёжного закрепления свариваемых деталей и обеспечения электрического контакта');
UNLOCK TABLES;

CREATE OR REPLACE VIEW tools_in_use AS
SELECT
    t.id_tool AS tool_id,
    tt.name AS tool_type_name,
    e.surname,
    e.name,
    e.patronymic,
    p.title_position,
    e.phone_number,
    e.email,
    hti.date_and_time_issue AS issuance_date
FROM tools t
LEFT JOIN types_of_tools tt ON t.article_tool_type = tt.article_tool_type
LEFT JOIN history_tool_issue hti ON t.id_tool = hti.id_tool
LEFT JOIN employees e ON hti.id_employee = e.id_employee
JOIN positions p ON e.id_position = p.id_position
WHERE t.availability = 0
  AND hti.date_and_time_issue = (
      SELECT MAX(inner_hti.date_and_time_issue)
      FROM history_tool_issue inner_hti
      WHERE inner_hti.id_tool = t.id_tool
  );

CREATE OR REPLACE VIEW tools_full_info AS
SELECT
    t.id_tool,
    tt.name AS tool_type_name,
    CONCAT(sl.name, ' ', sl.id_place) AS storage_name,
    t.availability,
    t.date_and_time_admission AS delivery_date
FROM tools t
JOIN storage_locations sl ON t.id_place = sl.id_place
JOIN types_of_tools tt ON t.article_tool_type = tt.article_tool_type
LEFT JOIN history_write_off_instrument hw ON t.id_tool = hw.id_tool
WHERE hw.id_tool IS NULL;

DELIMITER $$
CREATE PROCEDURE get_tools_in_use_by_employee(IN employee_id_input VARCHAR(8))
BEGIN
    SELECT
        t.id_tool,
        tt.name AS tool_type_name,
        hti.date_and_time_issue AS issuance_date
    FROM tools t
    JOIN types_of_tools tt ON t.article_tool_type = tt.article_tool_type
    JOIN history_tool_issue hti ON t.id_tool = hti.id_tool
    LEFT JOIN history_write_off_instrument hw ON t.id_tool = hw.id_tool
    WHERE hti.id_employee = employee_id_input
      AND hti.action = 'Выдан'
      AND hw.id_tool IS NULL
      AND hti.date_and_time_issue = (
          SELECT MAX(inner_hti.date_and_time_issue)
          FROM history_tool_issue inner_hti
          WHERE inner_hti.id_tool = t.id_tool
      );
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE get_available_tools_in_storage(IN storage_id_input VARCHAR(8))
BEGIN
    SELECT
        t.id_tool,
        tt.name AS tool_type_name,
        t.date_and_time_admission AS delivery_date
    FROM tools t
    JOIN types_of_tools tt ON t.article_tool_type = tt.article_tool_type
    LEFT JOIN history_write_off_instrument hw ON t.id_tool = hw.id_tool
    WHERE t.id_place = storage_id_input
      AND t.availability = 1
      AND hw.id_tool IS NULL;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_tool(
    IN tool_type_article VARCHAR(8),
    IN storage_id_input VARCHAR(8)
)
BEGIN
    DECLARE max_number INT DEFAULT 0;
    DECLARE new_id_tool VARCHAR(8);

    -- Поиск максимального порядкового номера среди существующих инструментов такого типа
    SELECT IFNULL(MAX(CAST(SUBSTRING_INDEX(id_tool, '-', -1) AS UNSIGNED)), 0)
    INTO max_number
    FROM (
        SELECT id_tool FROM tools WHERE article_tool_type = tool_type_article
        UNION
        SELECT id_tool FROM history_write_off_instrument WHERE id_tool LIKE CONCAT('T', tool_type_article, '-%')
    ) AS combined;

    -- Генерация нового ID инструмента
    SET new_id_tool = CONCAT('T', tool_type_article, '-', max_number + 1);

    -- Вставка нового инструмента
    INSERT INTO tools (id_tool, article_tool_type, id_place, availability, date_and_time_admission)
    VALUES (new_id_tool, tool_type_article, storage_id_input, 1, NOW());
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE add_tool_batch(
    IN tool_type_article VARCHAR(8),
    IN storage_id_input VARCHAR(8),
    IN tool_count INT
)
BEGIN
    DECLARE max_number INT DEFAULT 0;
    DECLARE i INT DEFAULT 1;
    DECLARE new_id_tool VARCHAR(20);

    -- Поиск максимального порядкового номера среди существующих инструментов такого типа
    SELECT IFNULL(MAX(CAST(SUBSTRING_INDEX(id_tool, '-', -1) AS UNSIGNED)), 0)
    INTO max_number
    FROM (
        SELECT id_tool FROM tools WHERE article_tool_type = tool_type_article
        UNION
        SELECT id_tool FROM history_write_off_instrument WHERE id_tool LIKE CONCAT('T', tool_type_article, '-%')
    ) AS combined;

    -- Добавление инструментов по одному 
    WHILE i <= tool_count DO
        SET new_id_tool = CONCAT('T', tool_type_article, '-', max_number + i);
        INSERT INTO tools (id_tool, article_tool_type, id_place, availability, date_and_time_admission)
        VALUES (new_id_tool, tool_type_article, storage_id_input, 1, NOW());
        SET i = i + 1;
    END WHILE;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE write_off_tool(IN input_id_tool VARCHAR(20))
BEGIN
    DECLARE tool_type_article VARCHAR(8);
    DECLARE tool_type_name VARCHAR(64);
    DECLARE new_id_write_off VARCHAR(20);
    DECLARE max_number INT DEFAULT 0;

    -- Получение артикула типа инструмента
    SELECT article_tool_type INTO tool_type_article
    FROM tools
    WHERE id_tool = input_id_tool;

    -- Получаение названия типа инструмента
    SELECT name INTO tool_type_name
    FROM types_of_tools
    WHERE article_tool_type = tool_type_article;
    
    -- Получение максимального номера списания
    SELECT MAX(CAST(SUBSTRING(id_write_off, 4) AS UNSIGNED))
    INTO max_number
    FROM history_write_off_instrument;
    -- Формирование нового кода списания
    SET new_id_write_off = CONCAT('WRF', max_number + 1);

    INSERT INTO history_write_off_instrument (id_write_off, id_tool, name, date_and_time_write_off)
    VALUES (new_id_write_off, input_id_tool, tool_type_name, NOW());

    -- Удаление записей из history_tool_issue (если они есть)
    DELETE FROM history_tool_issue
    WHERE id_tool = input_id_tool;

    -- Удаление инструмента из таблицы tools
    DELETE FROM tools WHERE id_tool = input_id_tool;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER tools_force_available
BEFORE INSERT ON tools
FOR EACH ROW
BEGIN
    SET NEW.availability = 1;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER issuance_manage_availability
BEFORE INSERT ON history_tool_issue
FOR EACH ROW
BEGIN
    DECLARE currentStatus TINYINT;

    -- Получение текущего статуса availability
    SELECT availability INTO currentStatus
    FROM tools
    WHERE id_tool = NEW.id_tool;

    IF NEW.action = 'Возврат' THEN
        IF currentStatus = 1 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Инструмент уже находится на складе — возврат невозможен';
        ELSE
            UPDATE tools SET availability = 1 WHERE id_tool = NEW.id_tool;
        END IF;

    ELSEIF NEW.action = 'Выдан' THEN
        IF currentStatus = 0 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Инструмент уже выдан — повторная выдача невозможна';
        ELSE
            UPDATE tools SET availability = 0 WHERE id_tool = NEW.id_tool;
        END IF;

    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Недопустимое значение поля action';
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER issuance_delete_toggle_availability
AFTER DELETE ON history_tool_issue
FOR EACH ROW
BEGIN
    IF OLD.action = 'Выдан' THEN
        UPDATE tools SET availability = 1 WHERE id_tool = OLD.id_tool;
    ELSEIF OLD.action = 'Возврат' THEN
        UPDATE tools SET availability = 0 WHERE id_tool = OLD.id_tool;
    END IF;
END$$
DELIMITER ;

CREATE ROLE admin;
GRANT ALL PRIVILEGES ON *.* TO admin WITH GRANT OPTION;

CREATE ROLE worker;
GRANT SELECT ON tools_in_use TO worker;
GRANT SELECT ON tools_full_info TO worker;
GRANT EXECUTE ON PROCEDURE get_tools_in_use_by_employee TO worker;
GRANT EXECUTE ON PROCEDURE get_available_tools_in_storage TO worker;

CREATE ROLE storekeeper;
GRANT SELECT ON tools_in_use TO storekeeper;
GRANT SELECT ON tools_full_info TO storekeeper;
GRANT SELECT ON employees TO storekeeper;
GRANT SELECT ON history_tool_issue TO storekeeper;
GRANT SELECT ON history_write_off_instrument TO storekeeper;
GRANT SELECT ON storage_locations TO storekeeper;
GRANT SELECT ON tools TO storekeeper;
GRANT SELECT ON types_of_tools TO storekeeper;
GRANT INSERT, DELETE ON history_tool_issue TO storekeeper;
GRANT INSERT, DELETE ON types_of_tools TO storekeeper;
GRANT INSERT, DELETE ON tools TO storekeeper;
GRANT UPDATE (id_place, date_and_time_admission) ON tools TO storekeeper;
GRANT EXECUTE ON PROCEDURE get_tools_in_use_by_employee TO storekeeper;
GRANT EXECUTE ON PROCEDURE get_available_tools_in_storage TO storekeeper;
GRANT EXECUTE ON PROCEDURE add_tool TO storekeeper;
GRANT EXECUTE ON PROCEDURE add_tool_batch TO storekeeper;
GRANT EXECUTE ON PROCEDURE write_off_tool TO storekeeper;

SET DEFAULT ROLE worker TO 'worker'@'localhost';
SET DEFAULT ROLE storekeeper TO 'storekeeper'@'localhost';