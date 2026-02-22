-- liquibase formatted sql

-- changeset ronal:1771773568106-1
CREATE TABLE users
(
    id                UUID                                   NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    name              VARCHAR(70)                            NOT NULL,
    email             VARCHAR(150)                           NOT NULL,
    password_hash     TEXT,
    avatar_url        TEXT,
    is_email_verified BOOLEAN                                NOT NULL,
    status            VARCHAR(255)                           NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_email UNIQUE (email)
);
