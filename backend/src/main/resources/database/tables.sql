DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "role";
DROP TABLE IF EXISTS "user_role";

CREATE TABLE "role" (
  role_pk     SERIAL PRIMARY KEY  NOT NULL,
  name        VARCHAR(45)         NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE "user" (
  user_pk      SERIAL PRIMARY KEY NOT NULL,
  login        VARCHAR(45)        NOT NULL,
  password     VARCHAR(255)       NOT NULL,
  first_name   VARCHAR(45)        NOT NULL,
  last_name    VARCHAR(45)        NOT NULL,
  phone_number VARCHAR(45),
  email        VARCHAR(45)        NOT NULL,
  manager      INT
);

CREATE TABLE "user_role" (
  ur_pk   SERIAL PRIMARY KEY NOT NULL,
  user_fk INT                NOT NULL,
  role_fk INT                NOT NULL
);