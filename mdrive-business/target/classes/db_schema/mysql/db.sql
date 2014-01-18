-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'USER'
--
-- ---

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `user_type_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'USER_TYPE'
-- ID = PASSENGER|DRIVER
-- ---

DROP TABLE IF EXISTS `USER_TYPE`;

CREATE TABLE `USER_TYPE` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `name_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='ID = PASSENGER|DRIVER';

-- ---
-- Table 'GO_BID'
--
-- ---

DROP TABLE IF EXISTS `GO_BID`;

CREATE TABLE `GO_BID` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `user_id` INTEGER NOT NULL,
  `from_geo_object_id` INTEGER NOT NULL,
  `to_geo_object_id` INTEGER NOT NULL,
  `via_geo_object_id` INTEGER DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'GO_REPLY'
--
-- ---

DROP TABLE IF EXISTS `GO_REPLY`;

CREATE TABLE `GO_REPLY` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `go_bid_id` INTEGER DEFAULT NULL,
  `user_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Table 'GEO_OBJECT'
-- Geo Objects hierarchy: Parent contain Child
-- ---

DROP TABLE IF EXISTS `GEO_OBJECT`;

CREATE TABLE `GEO_OBJECT` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `name_id` INTEGER NOT NULL,
  `parent_id` INTEGER NOT NULL DEFAULT NULL,
  `geo_object_type_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='Geo Objects hierarchy: Parent contain Child';

-- ---
-- Table 'NAME'
-- Name Dictionary, Multi Lingual. Primary key is ID + LANGUAGE combination
-- ---

DROP TABLE IF EXISTS `NAME`;

CREATE TABLE `NAME` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `language` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='Name Dictionary, Multi Lingual. Primary key is ID + LANGUAGE';

-- ---
-- Table 'GEO_OBJECT_TYPE'
-- OUNTRY, CITY, CITY_PART, STREET, BUILDING
-- ---

DROP TABLE IF EXISTS `GEO_OBJECT_TYPE`;

CREATE TABLE `GEO_OBJECT_TYPE` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `name_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='OUNTRY, CITY, CITY_PART, STREET, BUILDING';

-- ---
-- Table 'COORDINATES'
-- Coordinates Information (BoundedBy, Center)
-- ---

DROP TABLE IF EXISTS `COORDINATES`;

CREATE TABLE `COORDINATES` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `geo_object_id` INTEGER NOT NULL,
  `center_long` INTEGER NOT NULL,
  `center_lat` INTEGER NOT NULL,
  `lower_corner_long` INTEGER NOT NULL,
  `lower_corner_lat` INTEGER NOT NULL,
  `upper_corner_long` INTEGER NOT NULL,
  `upper_corner_lat` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT='Coordinates Information (BoundedBy, Center)';

-- ---
-- Table 'SETTINGS'
--
-- ---

DROP TABLE IF EXISTS `SETTINGS`;

CREATE TABLE `SETTINGS` (
  `id` INTEGER AUTO_INCREMENT DEFAULT NULL,
  `user_id` INTEGER NOT NULL,
  PRIMARY KEY (`id`)
);

-- ---
-- Foreign Keys
-- ---

ALTER TABLE `USER` ADD FOREIGN KEY (user_type_id) REFERENCES `USER_TYPE` (`id`);
ALTER TABLE mdrive.USER_TYPE ADD FOREIGN KEY (name_id) REFERENCES mdrive.NAME (id);
ALTER TABLE `GO_BID` ADD FOREIGN KEY (user_id) REFERENCES `USER` (`id`);
ALTER TABLE `GO_BID` ADD FOREIGN KEY (from_geo_object_id) REFERENCES `GEO_OBJECT` (`id`);
ALTER TABLE `GO_BID` ADD FOREIGN KEY (to_geo_object_id) REFERENCES `GEO_OBJECT` (`id`);
ALTER TABLE `GO_BID` ADD FOREIGN KEY (via_geo_object_id) REFERENCES `GEO_OBJECT` (`id`);
ALTER TABLE `GO_REPLY` ADD FOREIGN KEY (go_bid_id) REFERENCES `GO_BID` (`id`);
ALTER TABLE `GO_REPLY` ADD FOREIGN KEY (user_id) REFERENCES `USER` (`id`);
ALTER TABLE `GEO_OBJECT` ADD FOREIGN KEY (name_id) REFERENCES `NAME` (`id`);
ALTER TABLE `GEO_OBJECT` ADD FOREIGN KEY (parent_id) REFERENCES `GEO_OBJECT` (`id`);
ALTER TABLE `GEO_OBJECT` ADD FOREIGN KEY (geo_object_type_id) REFERENCES `GEO_OBJECT_TYPE` (`id`);
ALTER TABLE `GEO_OBJECT_TYPE` ADD FOREIGN KEY (name_id) REFERENCES `NAME` (`id`);
ALTER TABLE `COORDINATES` ADD FOREIGN KEY (geo_object_id) REFERENCES `GEO_OBJECT` (`id`);
ALTER TABLE `SETTINGS` ADD FOREIGN KEY (user_id) REFERENCES `USER` (`id`);

-- ---
-- Table Properties
-- ---

-- ALTER TABLE `USER` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `USER_TYPE` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `GO_BID` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `GO_REPLY` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `GEO_OBJECT` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `NAME` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `GEO_OBJECT_TYPE` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `COORDINATES` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `SETTINGS` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ---
-- Test Data
-- ---

-- INSERT INTO `USER` (`id`,`user_type_id`) VALUES
-- ('','');
-- INSERT INTO `USER_TYPE` (`id`,`name_id`) VALUES
-- ('','');
-- INSERT INTO `GO_BID` (`id`,`user_id`,`from_geo_object_id`,`to_geo_object_id`,`via_geo_object_id`) VALUES
-- ('','','','','');
-- INSERT INTO `GO_REPLY` (`id`,`go_bid_id`,`user_id`) VALUES
-- ('','','');
-- INSERT INTO `GEO_OBJECT` (`id`,`name_id`,`parent_id`,`geo_object_type_id`) VALUES
-- ('','','','');
-- INSERT INTO `NAME` (`id`,`language`) VALUES
-- ('','');
-- INSERT INTO `GEO_OBJECT_TYPE` (`id`,`name_id`) VALUES
-- ('','');
-- INSERT INTO `COORDINATES` (`id`,`geo_object_id`,`center_long`,`center_lat`,`lower_corner_long`,`lower_corner_lat`,`upper_corner_long`,`upper_corner_lat`) VALUES
-- ('','','','','','','','');
-- INSERT INTO `SETTINGS` (`id`,`user_id`) VALUES
-- ('','');