-- liquibase formatted sql

-- changeset ronal:1771783504601-1
CREATE TABLE user_auth_provider
(
    id               UUID                                   NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    user_id          UUID                                   NOT NULL,
    provider         VARCHAR(50)                            NOT NULL,
    provider_user_id VARCHAR(200)                           NOT NULL,
    email            VARCHAR(150),
    CONSTRAINT pk_user_auth_provider PRIMARY KEY (id),
    CONSTRAINT uk_provider_provider_user_id UNIQUE (provider, provider_user_id),
    CONSTRAINT uk_user_provider UNIQUE (user_id, provider),
    CONSTRAINT FK_USER_AUTH_PROVIDERS_USER FOREIGN KEY (user_id) REFERENCES users (id)
);
