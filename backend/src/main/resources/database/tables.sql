DROP TABLE IF EXISTS site_information;
DROP TABLE IF EXISTS site_information_types;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS offices;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;



CREATE TABLE roles (
  id			BIGSERIAL PRIMARY KEY  NOT NULL,
  name			VARCHAR(45)          NOT NULL,
  description	VARCHAR(255)
);

CREATE TABLE users (
  id     	 	BIGSERIAL PRIMARY KEY              NOT NULL,
  login			VARCHAR(45)                				 ,
  password		VARCHAR(255)                     NOT NULL,
  first_name	VARCHAR(45)                      NOT NULL,
  last_name		VARCHAR(45)                      NOT NULL,
  phone_number	VARCHAR(45),
  email			VARCHAR(45) UNIQUE               NOT NULL,
  manager		BIGINT,
  address_id   BIGINT,
  registration_date DATE

);

CREATE TABLE users_roles (
  id   BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT                 NOT NULL,
  role_id BIGINT                 NOT NULL
);

CREATE TABLE addresses (
  id BIGSERIAL PRIMARY KEY  NOT NULL,
  street     VARCHAR(45)          NOT NULL,
  house      VARCHAR(5)           NOT NULL,
  floor      SMALLINT             NOT NULL,
  flat       SMALLINT             NOT NULL
);

CREATE TABLE offices (
  id   BIGSERIAL PRIMARY KEY     NOT NULL,
  name        VARCHAR(45)             NOT NULL,
  address_id  BIGINT                     NOT NULL,
  description VARCHAR(300)
);

CREATE TABLE orders (
  id          BIGSERIAL PRIMARY KEY    NOT NULL,
  user_id           BIGINT                    NOT NULL,
  office_id         BIGINT                    			,
  order_status_id   BIGINT                    NOT NULL,
  sender_address_id BIGINT                    NOT NULL,
  receiver_address_id	BIGINT                NOT NULL,
  parent_id			BIGINT,
  shipping_time		TIMESTAMP,
  confirmation_time	TIMESTAMP,
  execution_time	TIMESTAMP,
  creation_time		TIMESTAMP,
  description       VARCHAR(300),
  feedback			VARCHAR(300)

);


CREATE TABLE order_status (
  id BIGSERIAL PRIMARY KEY    NOT NULL,
  name            VARCHAR(45) UNIQUE     NOT NULL,
  description     VARCHAR(300)
);

CREATE TABLE services (
  id  BIGSERIAL PRIMARY KEY NOT NULL,
  order_id    BIGINT                 NOT NULL,
  courier_id  BIGINT,
  operator_id BIGINT                 NOT NULL,
  date        TIMESTAMP           NOT NULL,
  attempt     SMALLINT            NOT NULL
);

CREATE TABLE site_information_types (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
    name	VARCHAR(30)	NOT NULL
);



CREATE TABLE site_information (
	id	BIGSERIAL PRIMARY KEY	NOT NULL,
    text					VARCHAR(300),
    admin_id 				BIGINT 				NOT NULL,
    type_id	BIGINT
);







