CREATE TABLE social_accounts
(
    id          INT AUTO_INCREMENT NOT NULL,
    provider    VARCHAR(20)        NOT NULL,
    provider_id VARCHAR(50)        NOT NULL,
    email       VARCHAR(150)       NOT NULL,
    name        VARCHAR(100)       NOT NULL,
    user_id     INT                NULL,
    CONSTRAINT pk_social_accounts PRIMARY KEY (id)
);

ALTER TABLE social_accounts
    ADD CONSTRAINT FK_SOCIAL_ACCOUNTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);