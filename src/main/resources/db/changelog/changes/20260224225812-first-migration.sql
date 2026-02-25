-- liquibase formatted sql

-- changeset ronal:1771984692121-1
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

-- changeset ronal:1771984692121-2
CREATE TABLE organization_invite
(
    id              UUID                                   NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    organization_id UUID                                   NOT NULL,
    invited_by      UUID                                   NOT NULL,
    invited_email   VARCHAR(150)                           NOT NULL,
    expires_at      TIMESTAMP WITHOUT TIME ZONE            NOT NULL,
    CONSTRAINT pk_organization_invite PRIMARY KEY (id)
);

-- changeset ronal:1771984692121-3
CREATE TABLE organization_member
(
    id              UUID                                   NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    organization_id UUID                                   NOT NULL,
    user_id         UUID                                   NOT NULL,
    status          VARCHAR(30)                            NOT NULL,
    joined_at       TIMESTAMP WITH TIME ZONE,
    left_at         TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_organization_member PRIMARY KEY (id)
);

-- changeset ronal:1771984692121-4
CREATE TABLE organization_member_role
(
    id                     UUID                                   NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE,
    organization_id        UUID                                   NOT NULL,
    organization_role_id   UUID                                   NOT NULL,
    organization_member_id UUID                                   NOT NULL,
    CONSTRAINT pk_organization_member_role PRIMARY KEY (id)
);

-- changeset ronal:1771984692121-5
CREATE TABLE organization_role
(
    id               UUID                                   NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    organization_id  UUID                                   NOT NULL,
    name             VARCHAR(80)                            NOT NULL,
    permissions_mask BIGINT                                 NOT NULL,
    system_key       VARCHAR(50),
    CONSTRAINT pk_organization_role PRIMARY KEY (id)
);

-- changeset ronal:1771984692121-6
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

-- changeset ronal:1771984692121-7
CREATE TABLE users
(
    id                UUID                                   NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    name              VARCHAR(70)                            NOT NULL,
    email             VARCHAR(150)                           NOT NULL,
    password_hash     TEXT,
    avatar_url        TEXT                                   NOT NULL,
    is_email_verified BOOLEAN                                NOT NULL,
    status            VARCHAR(255)                           NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset ronal:1771984692121-8
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset ronal:1771984692121-9
ALTER TABLE organization_invite
    ADD CONSTRAINT uk_organization_invite_organization_email UNIQUE (organization_id, invited_email);

-- changeset ronal:1771984692121-10
ALTER TABLE organization_member
    ADD CONSTRAINT uk_organization_member_organization_user UNIQUE (organization_id, user_id);

-- changeset ronal:1771984692121-11
ALTER TABLE organization_member_role
    ADD CONSTRAINT uk_organization_member_role UNIQUE (organization_member_id, organization_role_id);

-- changeset ronal:1771984692121-12
ALTER TABLE organization
    ADD CONSTRAINT uk_organization_slug UNIQUE (slug);

-- changeset ronal:1771984692121-13
ALTER TABLE user_auth_provider
    ADD CONSTRAINT uk_provider_provider_user_id UNIQUE (provider, provider_user_id);

-- changeset ronal:1771984692121-14
ALTER TABLE user_auth_provider
    ADD CONSTRAINT uk_user_provider UNIQUE (user_id, provider);

-- changeset ronal:1771984692121-16
ALTER TABLE organization_invite
    ADD CONSTRAINT FK_ORGANIZATION_INVITE_INVITED_BY_MEMBER FOREIGN KEY (invited_by) REFERENCES organization_member (id);

-- changeset ronal:1771984692121-17
ALTER TABLE organization_invite
    ADD CONSTRAINT FK_ORGANIZATION_INVITE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771984692121-18
ALTER TABLE organization_member
    ADD CONSTRAINT FK_ORGANIZATION_MEMBER_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771984692121-19
ALTER TABLE organization_member_role
    ADD CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_MEMBER FOREIGN KEY (organization_member_id) REFERENCES organization_member (id);

-- changeset ronal:1771984692121-20
ALTER TABLE organization_member_role
    ADD CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771984692121-21
ALTER TABLE organization_member_role
    ADD CONSTRAINT FK_ORGANIZATION_MEMBER_ROLE_ROLE FOREIGN KEY (organization_role_id) REFERENCES organization_role (id);

-- changeset ronal:1771984692121-22
ALTER TABLE organization_member
    ADD CONSTRAINT FK_ORGANIZATION_MEMBER_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset ronal:1771984692121-23
ALTER TABLE organization_role
    ADD CONSTRAINT FK_ORGANIZATION_ROLE_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771984692121-24
ALTER TABLE user_auth_provider
    ADD CONSTRAINT FK_USER_AUTH_PROVIDERS_USER FOREIGN KEY (user_id) REFERENCES users (id);

