-- liquibase formatted sql

-- changeset ronal:1771809401206-3
CREATE TABLE organization_member_role
(
    id                     UUID                                   NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE,
    organization_id        UUID                                   NOT NULL,
    organization_role_id   UUID                                   NOT NULL,
    organization_member_id UUID                                   NOT NULL,
    CONSTRAINT pk_organization_member_role PRIMARY KEY (id),
    CONSTRAINT uk_organization_member_role UNIQUE (organization_member_id, organization_role_id),
        CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_MEMBER FOREIGN KEY (organization_member_id) REFERENCES organization_member (id),
        CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id),
        CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_ROLE FOREIGN KEY (organization_role_id) REFERENCES organization_role (id)
);
