CREATE TABLE users
(
    id                  INT AUTO_INCREMENT NOT NULL,
    full_name           VARCHAR(100)                DEFAULT '',
    phone_number        VARCHAR(10)        NOT NULL,
    address             VARCHAR(200)                DEFAULT '',
    password            VARCHAR(100)       NOT NULL DEFAULT '',
    created_at          datetime,
    updated_at          datetime,
    is_active           BIT(1)             NULL,
    date_of_birth       date               NULL,
    facebook_account_id INT                         DEFAULT 0,
    google_account_id   INT                         DEFAULT 0,
    role_id             INT                NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE tokens
(
    id              INT AUTO_INCREMENT  NOT NULL,
    token           VARCHAR(255) UNIQUE NOT NULL,
    token_type      VARCHAR(50)         NOT NULL,
    expiration_date datetime            NULL,
    revoked         BIT(1)              NOT NULL,
    expired         BIT(1)              NOT NULL,
    user_id         INT                 NULL,
    CONSTRAINT pk_tokens PRIMARY KEY (id)
);

ALTER TABLE tokens
    ADD CONSTRAINT FK_TOKENS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE roles
(
    id   INT         NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT FK_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);