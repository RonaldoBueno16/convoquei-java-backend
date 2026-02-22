-- liquibase formatted sql

-- changeset ronal:1771789128234-1
CREATE TABLE organization
(
    id         UUID                                   NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(70)                            NOT NULL,
    slug       VARCHAR(150)                           NOT NULL,
    photo_url  TEXT,
    status     VARCHAR(30)                            NOT NULL,
    CONSTRAINT pk_organization PRIMARY KEY (id),
    CONSTRAINT uk_organization_slug UNIQUE (slug)
);

