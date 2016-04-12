drop database if exists grouptalkdb;
create database grouptalkdb;

use grouptalkdb;

CREATE TABLE users (
        id BINARY(16) NOT NULL,
        loginid VARCHAR(15) NOT NULL UNIQUE,
        password BINARY(16) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE user_roles (
        userid BINARY(16) NOT NULL,
        role ENUM ('registered','admin'),
        FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
        PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
        userid BINARY(16) NOT NULL,
        token BINARY(16) NOT NULL,
        FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
        PRIMARY KEY (token)
);
CREATE TABLE groups (
	id BINARY(16) NOT NULL,
	nombre VARCHAR(15) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE relacionUserGrupo (
	userid BINARY(16) NOT NULL,
	groupid BINARY(16) NOT NULL,
	FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
	FOREIGN KEY (groupid) REFERENCES groups(id) on delete cascade, 
	PRIMARY KEY (userid,groupid)
);


CREATE TABLE tema (
	id BINARY(16) NOT NULL,
	idgrupo BINARY(16) NOT NULL,
	titulo VARCHAR(100) NOT NULL,
	content VARCHAR(100) NOT NULL,
	autor_tema BINARY (16) NOT NULL,
	FOREIGN KEY (autor_tema) REFERENCES users (id) on delete cascade,
	FOREIGN KEY (idgrupo) REFERENCES groups(id) on delete cascade,
	PRIMARY KEY (id)
);

CREATE TABLE respuesta (
	id BINARY(16) NOT NULL,
	idtema BINARY(16) NOT NULL,
	content VARCHAR(100) NOT NULL,
	autor_respuesta BINARY(16) NOT NULL,
	FOREIGN KEY (idtema) REFERENCES tema(id) on delete cascade,
	PRIMARY KEY (id)
);

