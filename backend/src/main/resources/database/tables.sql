DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS offices;
DROP TABLE IF EXISTS addresses;


CREATE TABLE roles (
  role_pk     SERIAL4 PRIMARY KEY  NOT NULL,
  name        VARCHAR(45)          NOT NULL,
  description VARCHAR(255)
);



CREATE TABLE users (
  user_pk      SERIAL4 PRIMARY KEY              NOT NULL,
  login        VARCHAR(45) UNIQUE               NOT NULL,
  password     VARCHAR(255)                     NOT NULL,
  first_name   VARCHAR(45)                      NOT NULL,
  last_name    VARCHAR(45)                      NOT NULL,
  phone_number VARCHAR(45),
  email        VARCHAR(45) UNIQUE               NOT NULL,
  manager      INT,
  address_fk   INT
);

CREATE TABLE users_roles (
  user_role_pk   SERIAL4 PRIMARY KEY NOT NULL,
  user_fk INT                 NOT NULL,
  role_fk INT                 NOT NULL
);

CREATE TABLE addresses (
  address_pk SERIAL4 PRIMARY KEY  NOT NULL,
  street     VARCHAR(45)          NOT NULL,
  house      VARCHAR(5)           NOT NULL,
  floor      SMALLINT             NOT NULL,
  flat       SMALLINT             NOT NULL
);

CREATE TABLE offices (
  office_pk   SERIAL4 PRIMARY KEY     NOT NULL,
  name        VARCHAR(45)             NOT NULL,
  address_fk  INT                     NOT NULL,
  description VARCHAR(300)
);

CREATE TABLE orders (
  order_pk          SERIAL4 PRIMARY KEY    NOT NULL,
  user_fk           INT                    NOT NULL,
  office_fk         INT                    NOT NULL,
  order_status_fk   INT                    NOT NULL,
  client_address_fk INT                    NOT NULL,
  parent_fk			INT,				
  time              TIMESTAMP,
  description       VARCHAR(300)
);

CREATE TABLE feedbacks (
  feedback_pk SERIAL4 PRIMARY KEY  NOT NULL,
  order_fk    INT                  NOT NULL,
  text        VARCHAR(300)
);

CREATE TABLE order_status (
  order_status_pk SERIAL4 PRIMARY KEY    NOT NULL,
  name            VARCHAR(45) UNIQUE     NOT NULL,
  description     VARCHAR(300)
);

CREATE TABLE services (
  service_pk  SERIAL4 PRIMARY KEY NOT NULL,
  order_fk    INT                 NOT NULL,
  courier_fk  INT,
  operator_fk INT                 NOT NULL,
  date        TIMESTAMP           NOT NULL,
  attempt     SMALLINT            NOT NULL
);

CREATE TABLE site_information (
	service_information_pk	SERIAL4 PRIMARY KEY	NOT NULL,
    text					VARCHAR(300),
    admin_fk 				INT 				NOT NULL,
    type	INT
);

