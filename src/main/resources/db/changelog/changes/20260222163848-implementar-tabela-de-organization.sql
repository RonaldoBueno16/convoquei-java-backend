-- liquibase formatted sql

-- changeset ronal:1771789128234-5
CREATE TABLE organization
(
    id         UUID                                   NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(70)                            NOT NULL,
    slug       VARCHAR(150)                           NOT NULL,
    photo_url  TEXT,
    status     VARCHAR(30)                            NOT NULL,
    CONSTRAINT pk_organization PRIMARY KEY (id)
);

-- changeset ronal:1771789128234-6
ALTER TABLE organization
    ADD CONSTRAINT uk_organization_slug UNIQUE (slug);

-- changeset ronal:1771789128234-2
CREATE INDEX idx_uap_provider_provider_user_id ON user_auth_provider (provider, provider_user_id);

