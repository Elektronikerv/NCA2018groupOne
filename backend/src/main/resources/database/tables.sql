DROP TABLE IF EXISTS site_information;
DROP TABLE IF EXISTS working_days;
DROP TABLE IF EXISTS adverts;
DROP TABLE IF EXISTS advert_types;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS fulfillment_orders;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS offices;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;


CREATE TABLE roles (
  id          BIGSERIAL PRIMARY KEY  NOT NULL,
  name        VARCHAR(45)            NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE users (
  id                  BIGSERIAL PRIMARY KEY  NOT NULL,
  email               VARCHAR(45) UNIQUE     NOT NULL,
  password            VARCHAR(255)           NOT NULL,
  first_name          VARCHAR(45)            NOT NULL,
  last_name           VARCHAR(45)            NOT NULL,
  phone_number        VARCHAR(45)            NOT NULL,
  current_position_id BIGINT,
  manager_id          BIGINT,
  address_id          BIGINT,
  registration_date   TIMESTAMP

);

CREATE TABLE users_roles (
  id      BIGSERIAL PRIMARY KEY NOT NULL,
  user_id BIGINT                NOT NULL,
  role_id BIGINT                NOT NULL
);

CREATE TABLE addresses (
  id     BIGSERIAL PRIMARY KEY  NOT NULL,
  street VARCHAR(45)            NOT NULL,
  house  VARCHAR(5)             NOT NULL,
  floor  INTEGER,
  flat   INTEGER
);

CREATE TABLE offices (
  id           BIGSERIAL PRIMARY KEY  NOT NULL,
  name         VARCHAR(45)            NOT NULL,
  address_id   BIGINT                 NOT NULL,
  phone_number VARCHAR(45),
  description  VARCHAR(300)
);

CREATE TABLE orders (
  id                              BIGSERIAL PRIMARY KEY  NOT NULL,
  user_id                         BIGINT                 NOT NULL,
  office_id                       BIGINT,
  order_status_id                 BIGINT                 NOT NULL,
  sender_address_id               BIGINT,
  receiver_address_id             BIGINT,
  parent_id                       BIGINT,
  execution_time                  TIMESTAMP,
  creation_time                   TIMESTAMP,
  receiver_availability_time_from TIMESTAMP,
  receiver_availability_time_to   TIMESTAMP,
  description                     VARCHAR(300),
  feedback                        VARCHAR(300)

);


CREATE TABLE order_status (
  id          BIGSERIAL PRIMARY KEY  NOT NULL,
  name        VARCHAR(45) UNIQUE     NOT NULL,
  description VARCHAR(300)
);

CREATE TABLE fulfillment_orders (
  id                BIGSERIAL PRIMARY KEY NOT NULL,
  order_id          BIGINT                NOT NULL,
  ccagent_id        BIGINT,
  courier_id        BIGINT,
  confirmation_time TIMESTAMP,
  receiving_time    TIMESTAMP,
  shipping_time     TIMESTAMP,
  attempt           SMALLINT
);

CREATE TABLE advert_types (
  id   BIGSERIAL PRIMARY KEY  NOT NULL,
  name VARCHAR(30)            NOT NULL
);


CREATE TABLE adverts (
  id                 BIGSERIAL PRIMARY KEY  NOT NULL,
  header             VARCHAR(50)            NOT NULL,
  text               VARCHAR(1000)          NOT NULL,
  admin_id           BIGINT                 NOT NULL,
  type_id            BIGINT                 NOT NULL,
  date_of_publishing TIMESTAMP
);


CREATE TABLE working_days (
  id            BIGSERIAL PRIMARY KEY  NOT NULL,
  user_id       BIGINT                 NOT NULL,
  workday_start TIMESTAMP              NOT NULL,
  workday_end   TIMESTAMP              NOT NULL,
  worked_out    BOOLEAN
);
