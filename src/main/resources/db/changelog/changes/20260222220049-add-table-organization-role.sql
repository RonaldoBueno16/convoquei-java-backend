-- liquibase formatted sql

-- changeset ronal:1771808449324-1
CREATE TABLE organization_role
(
    id               UUID                                   NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    organization_id  UUID                                   NOT NULL,
    name             VARCHAR(80)                            NOT NULL,
    permissions_mask BIGINT                                 NOT NULL,
    is_system        BOOLEAN                                NOT NULL,
    CONSTRAINT pk_organization_role PRIMARY KEY (id),
    CONSTRAINT FK_ORGANIZATION_ROLE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id)
);