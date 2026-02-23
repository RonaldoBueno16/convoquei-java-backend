-- liquibase formatted sql

-- changeset ronal:1771816125232-3
CREATE TABLE organization_invite
(
    id              UUID                                   NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    organization_id UUID                                   NOT NULL,
    invited_by      UUID                                   NOT NULL,
    invited_email   VARCHAR(150)                           NOT NULL,
    expires_at      TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
    CONSTRAINT pk_organization_invite PRIMARY KEY (id),
    CONSTRAINT uk_organization_invite_organization_email UNIQUE (organization_id, invited_email),
    CONSTRAINT FK_ORGANIZATION_INVITE_INVITED_BY_MEMBER FOREIGN KEY (invited_by) REFERENCES organization_member (id),
    CONSTRAINT FK_ORGANIZATION_INVITE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id)
);

