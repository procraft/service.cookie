-- Initial {{cookiecutter.service_folder}} schema
--
-- Prepare database before:
-- CREATE ROLE libicraft LOGIN;
-- CREATE DATABASE {{cookiecutter.service_package}} OWNER libicraft TEMPLATE template0;

# --- !Ups
CREATE TYPE "AppleKind" AS ENUM ('Good', 'Bad');

CREATE TABLE apples(
  id SERIAL NOT NULL PRIMARY KEY,
  color VARCHAR NOT NULL,
  kind "AppleKind" NOT NULL
);

# --- !Downs
-- Tested: false

DROP TABLE IF EXISTS apples;
DROP TYPE IF EXISTS "AppleKind";
