DROP DATABASE IF EXISTS ab180;
CREATE DATABASE ab180;

CREATE USER 'ab180_user'@'localhost' IDENTIFIED BY '4pNov23!';

GRANT ALL PRIVILEGES ON * . * TO 'ab180_user'@'localhost';

FLUSH PRIVILEGES;

USE ab180;

CREATE TABLE IF NOT EXISTS short_link(
	shortId char(5) NOT NULL,
    aliasName varchar(25) NULL, -- up to 25 characters
	createdAt varchar(24) NOT NULL,
	url varchar(2048) NOT NULL, -- limit to 2048 characters for now
	UNIQUE ( shortId ),
	INDEX ( aliasName )
) ENGINE=InnoDB;