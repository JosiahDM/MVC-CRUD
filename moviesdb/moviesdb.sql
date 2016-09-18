-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema moviesdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `moviesdb` ;

-- -----------------------------------------------------
-- Schema moviesdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `moviesdb` DEFAULT CHARACTER SET utf8 ;
USE `moviesdb` ;

-- -----------------------------------------------------
-- Table `movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movie` ;

CREATE TABLE IF NOT EXISTS `movie` (
  `name` VARCHAR(100) NOT NULL,
  `mpaa_rating` VARCHAR(8) NOT NULL DEFAULT '',
  `description` VARCHAR(500) NOT NULL DEFAULT '',
  `image` VARCHAR(100) NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `year` VARCHAR(4) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `genre` ;

CREATE TABLE IF NOT EXISTS `genre` (
  `name` VARCHAR(20) NOT NULL,
  `id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `movie_genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `movie_genre` ;

CREATE TABLE IF NOT EXISTS `movie_genre` (
  `movie_id` INT NOT NULL,
  `genre_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`movie_id`, `genre_id`))
ENGINE = InnoDB;

CREATE INDEX `movie_to_genre_idx` ON `movie_genre` (`genre_id` ASC);


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_data` ;

CREATE TABLE IF NOT EXISTS `user_data` (
  `movie_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `rating` TINYINT(1) NOT NULL DEFAULT 0,
  `user_id` INT NOT NULL,
  `notes` VARCHAR(45) NULL,
  `watched` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE INDEX `user_data_to_user_idx` ON `user_data` (`user_id` ASC);

CREATE INDEX `user_data_to_movie_idx` ON `user_data` (`movie_id` ASC);

SET SQL_MODE = '';
GRANT USAGE ON *.* TO application;
 DROP USER application;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'application' IDENTIFIED BY 'application';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'application';

-- -----------------------------------------------------
-- Data for table `movie`
-- -----------------------------------------------------
START TRANSACTION;
USE `moviesdb`;
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('Willy Wonka & the Chocolate Factory', 'G', 'Charlie receives a golden ticket to a factory, his sweet tooth wants going into the lushing candy, it turns out there\'s an adventure in everything.', 'willywonka.jpg', 1, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('American Beauty', 'R', 'A sexually frustrated suburban father has a mid-life crisis after becoming infatuated with his daughter\'s best friend.', 'americanbeauty.jpg', 2, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('A Clockwork Orange', 'X', 'In future Britain, charismatic delinquent Alex DeLarge is jailed and volunteers for an experimental aversion therapy developed by the government in an effort to solve society\'s crime problem - but not all goes according to plan.', 'aclockworkorange.jpg', 3, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('The Hitchhiker\'s Guide to the Galaxy', 'PG', 'Mere seconds before the Earth is to be demolished by an alien construction crew, journeyman Arthur Dent is swept off the planet by his friend Ford Prefect, a researcher penning a new edition of \"The Hitchhiker\'s Guide to the Galaxy.\"', 'hitchhikersguide.jpg', 4, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('Guardians of the Galaxy', 'PG-13', 'A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe.', 'guardiansofthegalaxy.jpg', 5, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('V/H/S', 'R', 'When a group of misfits are hired by an unknown third party to burglarize a desolate house and acquire a rare VHS tape, they discover more found footage than they bargained for.', 'VHS.jpg', 6, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('Neighbors', 'R', 'After they are forced to live next to a fraternity house, a couple with a newborn baby do whatever they can to take them down.', 'neighbors.jpg', 7, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('Sausage Party', 'R', 'A sausage strives to discover the truth about his existence.', 'sausageparty.jpg', 8, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('Fight Club', 'R', 'An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soap maker, forming an underground fight club that evolves into something much, much more.', 'fightclub.jpg', 9, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('The Babadook', 'R', 'A single mother, plagued by the violent death of her husband, battles with her son\'s fear of a monster lurking in the house, but soon discovers a sinister presence all around her.', 'thebabadook.jpg', 10, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('The SpongeBob Movie: Sponge Out of Water', 'PG', 'When a diabolical pirate above the sea steals the secret Krabby Patty formula, SpongeBob and his nemesis Plankton must team up in order to get it back.', 'spongebob.jpg', 11, DEFAULT);
INSERT INTO `movie` (`name`, `mpaa_rating`, `description`, `image`, `id`, `year`) VALUES ('The Running Man', 'R', 'A wrongly convicted man must try to survive a public execution gauntlet staged as a game show.', 'runningman.jpg', 12, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `moviesdb`;
INSERT INTO `genre` (`name`, `id`) VALUES ('Action', 1);
INSERT INTO `genre` (`name`, `id`) VALUES ('Adventure', 2);
INSERT INTO `genre` (`name`, `id`) VALUES ('Animation', 3);
INSERT INTO `genre` (`name`, `id`) VALUES ('Biography', 4);
INSERT INTO `genre` (`name`, `id`) VALUES ('Comedy', 5);
INSERT INTO `genre` (`name`, `id`) VALUES ('Crime', 6);
INSERT INTO `genre` (`name`, `id`) VALUES ('Documentary', 7);
INSERT INTO `genre` (`name`, `id`) VALUES ('Drama', 8);
INSERT INTO `genre` (`name`, `id`) VALUES ('Family', 9);
INSERT INTO `genre` (`name`, `id`) VALUES ('Fantasy', 10);
INSERT INTO `genre` (`name`, `id`) VALUES ('Film-Noir', 11);
INSERT INTO `genre` (`name`, `id`) VALUES ('Game-Show', 12);
INSERT INTO `genre` (`name`, `id`) VALUES ('History', 13);
INSERT INTO `genre` (`name`, `id`) VALUES ('Horror', 14);
INSERT INTO `genre` (`name`, `id`) VALUES ('Music', 15);
INSERT INTO `genre` (`name`, `id`) VALUES ('Musical', 16);
INSERT INTO `genre` (`name`, `id`) VALUES ('Mystery', 17);
INSERT INTO `genre` (`name`, `id`) VALUES ('News', 18);
INSERT INTO `genre` (`name`, `id`) VALUES ('Reality-TV', 19);
INSERT INTO `genre` (`name`, `id`) VALUES ('Romance', 20);
INSERT INTO `genre` (`name`, `id`) VALUES ('Sci-Fi', 21);
INSERT INTO `genre` (`name`, `id`) VALUES ('Sport', 22);
INSERT INTO `genre` (`name`, `id`) VALUES ('Talk-Show', 23);
INSERT INTO `genre` (`name`, `id`) VALUES ('Thriller', 24);
INSERT INTO `genre` (`name`, `id`) VALUES ('War', 25);
INSERT INTO `genre` (`name`, `id`) VALUES ('Western', 26);

COMMIT;


-- -----------------------------------------------------
-- Data for table `movie_genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `moviesdb`;
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (1, 9);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (1, 10);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (1, 16);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (2, 8);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (2, 20);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (3, 6);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (3, 8);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (3, 21);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (4, 2);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (4, 5);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (4, 21);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (5, 1);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (5, 2);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (5, 21);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (6, 10);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (6, 14);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (7, 5);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (8, 3);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (8, 2);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (8, 5);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (9, 8);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (10, 8);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (10, 10);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (10, 14);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (11, 3);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (11, 2);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (11, 5);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (12, 1);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (12, 6);
INSERT INTO `movie_genre` (`movie_id`, `genre_id`) VALUES (12, 21);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `moviesdb`;
INSERT INTO `user` (`id`) VALUES (1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_data`
-- -----------------------------------------------------
START TRANSACTION;
USE `moviesdb`;
INSERT INTO `user_data` (`movie_id`, `id`, `rating`, `user_id`, `notes`, `watched`) VALUES (1, 1, 5, 1, 'Best movie evar.', 1);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
