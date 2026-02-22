-- liquibase formatted sql

-- changeset ronal:1771783504601-3
CREATE TABLE user_auth_provider
(
    id               UUID                                   NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    user_id          UUID                                   NOT NULL,
    provider         VARCHAR(50)                            NOT NULL,
    provider_user_id VARCHAR(200)                           NOT NULL,
    email            VARCHAR(150),
    CONSTRAINT pk_user_auth_provider PRIMARY KEY (id)
);

-- changeset ronal:1771783504601-4
ALTER TABLE user_auth_provider
    ADD CONSTRAINT uk_provider_provider_user_id UNIQUE (provider, provider_user_id);

-- changeset ronal:1771783504601-5
ALTER TABLE user_auth_provider
    ADD CONSTRAINT uk_user_provider UNIQUE (user_id, provider);

-- changeset ronal:1771783504601-6
CREATE INDEX idx_uap_provider_provider_user_id ON user_auth_provider (provider, provider_user_id);

-- changeset ronal:1771783504601-8
ALTER TABLE user_auth_provider
    ADD CONSTRAINT FK_USER_AUTH_PROVIDERS_USER FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX idx_uap_user_id ON user_auth_provider (user_id);
