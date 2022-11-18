DROP TABLE IF EXISTS app_user_institution;
DROP TABLE IF EXISTS institution;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS address_type;
DROP TABLE IF EXISTS app_user_institution;
DROP TABLE IF EXISTS role;
DROP SEQUENCE IF EXISTS seq_institution;
DROP SEQUENCE IF EXISTS seq_app_user;
DROP SEQUENCE IF EXISTS seq_address;

DROP SEQUENCE IF EXISTS public.hibernate_sequence;
CREATE SEQUENCE IF NOT EXISTS public.hibernate_sequence;
------------------------------------------------------------
CREATE TABLE IF NOT EXISTS address_type
(
    id   BIGINT UNIQUE      NOT NULL,
    type VARCHAR(65) UNIQUE NOT NULL,

    CONSTRAINT address_type_pk PRIMARY KEY (id)
);
------------------------------------------------------------
DROP SEQUENCE IF EXISTS seq_address;
CREATE SEQUENCE IF NOT EXISTS seq_address;
CREATE TABLE IF NOT EXISTS address
(
    id               BIGINT UNIQUE NOT NULL DEFAULT NEXTVAL('seq_address'),
    address_type_id  BIGINT        NOT NULL,
    country          VARCHAR(45)   NOT NULL,
    city             VARCHAR(45)   NOT NULL,
    address_line_one VARCHAR(500)  NOT NULL,
    address_line_two VARCHAR(500)  NULL,

    CONSTRAINT address_pk_id PRIMARY KEY (id),
    CONSTRAINT address_fk_address_type_id FOREIGN KEY (address_type_id) REFERENCES address_type (id)
);
------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS seq_institution;
CREATE TABLE IF NOT EXISTS institution
(
    id               BIGINT UNIQUE       NOT NULL DEFAULT NEXTVAL('seq_institution'),
    address_id       BIGINT UNIQUE,
    institution_name VARCHAR(45) UNIQUE NOT NULL,
    description      VARCHAR(500)        NULL,
    founded_date     DATE                NULL,
    date_added       TIMESTAMP           NOT NULL DEFAULT NOW()::TIMESTAMP,
    version          INT                 NOT NULL DEFAULT 0,

    CONSTRAINT institution_pk_id PRIMARY KEY (id),
    CONSTRAINT institution_address_id FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT institution_unique_address_id UNIQUE (address_id),
    CONSTRAINT institution_unique_institution_name UNIQUE (institution_name)
);
------------------------------------------------------------
CREATE TABLE IF NOT EXISTS role
(
    id        BIGINT UNIQUE      NOT NULL,
    role_name VARCHAR(45) UNIQUE NOT NULL,

    CONSTRAINT role_pk_id PRIMARY KEY (id),
    CONSTRAINT role_unique_role_name UNIQUE (role_name)
);
------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS seq_app_user;
CREATE TABLE IF NOT EXISTS app_user
(
    id           BIGINT UNIQUE       NOT NULL DEFAULT NEXTVAL('seq_app_user'),
    role_id      BIGINT              NOT NULL DEFAULT 1,
    address_id   BIGINT UNIQUE,
    first_name   VARCHAR(45)         NOT NULL,
    last_name    VARCHAR(45)         NOT NULL,
    email        VARCHAR(100) UNIQUE NOT NULL,
    username     VARCHAR(45) UNIQUE  NOT NULL,
    phone_number VARCHAR(11)         NULL,
    password     VARCHAR(100)        NOT NULL,
    dob          DATE                NULL,
    is_active    BOOLEAN             NOT NULL DEFAULT TRUE,
    date_added   TIMESTAMP           NOT NULL DEFAULT NOW()::TIMESTAMP,
    version      INT                 NOT NULL DEFAULT 0,

    CONSTRAINT app_user_id_pk PRIMARY KEY (id),
    CONSTRAINT app_user_role_id_fk FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT app_user_address_id_fk FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT app_user_address_unique UNIQUE (address_id),
    CONSTRAINT app_user_unique_email UNIQUE (email),
    CONSTRAINT app_user_unique_username UNIQUE (username)
);
------------------------------------------------------------
CREATE TABLE IF NOT EXISTS app_user_institution
(
    institution_id BIGINT NOT NULL,
    app_user_id    BIGINT NOT NULL,

    CONSTRAINT user_institution_pk PRIMARY KEY (institution_id, app_user_id),
    CONSTRAINT user_institution_fk_institution_id FOREIGN KEY (institution_id) references institution (id),
    CONSTRAINT user_institution_fk_app_user_id FOREIGN KEY (app_user_id) references app_user (id)
);