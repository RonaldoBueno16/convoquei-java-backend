-- liquibase formatted sql

-- changeset ronal:1771794300000-1
CREATE TABLE organization_member
(
    id               UUID                                   NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    organization_id  UUID                                   NOT NULL,
    user_id          UUID                                   NOT NULL,
    permissions_mask BIGINT,
    status           VARCHAR(30)                            NOT NULL,
    joined_at        TIMESTAMP WITH TIME ZONE,
    left_at          TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_organization_member PRIMARY KEY (id),
    CONSTRAINT uk_organization_member_organization_user UNIQUE (organization_id, user_id),
    CONSTRAINT fk_organization_member_organization FOREIGN KEY (organization_id) REFERENCES organization (id) ON DELETE CASCADE,
    CONSTRAINT fk_organization_member_user FOREIGN KEY (user_id) REFERENCES users (id)
);