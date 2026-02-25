-- liquibase formatted sql

-- changeset ronal:1771998329656-4
CREATE TABLE organization_group
(
    id              UUID                                   NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    organization_id UUID                                   NOT NULL,
    name            VARCHAR(70)                            NOT NULL,
    emoji           VARCHAR(10),
    CONSTRAINT pk_organization_group PRIMARY KEY (id)
);

-- changeset ronal:1771998329656-5
CREATE TABLE organization_group_participant
(
    id                     UUID                                   NOT NULL,
    created_at             TIMESTAMP WITH TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE,
    organization_group_id  UUID                                   NOT NULL,
    organization_id        UUID                                   NOT NULL,
    organization_member_id UUID                                   NOT NULL,
    joined_at              TIMESTAMP WITH TIME ZONE,
    CONSTRAINT pk_organization_group_participant PRIMARY KEY (id)
);

-- changeset ronal:1771998329656-6
ALTER TABLE organization_group_participant
    ADD CONSTRAINT uk_organization_group_participant UNIQUE (organization_group_id, organization_member_id);

-- changeset ronal:1771998329656-7
ALTER TABLE organization_group
    ADD CONSTRAINT FK_ORGANIZATION_GROUP_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771998329656-8
ALTER TABLE organization_group_participant
    ADD CONSTRAINT FK_ORGANIZATION_GROUP_PARTICIPANT_GROUP FOREIGN KEY (organization_group_id) REFERENCES organization_group (id);

-- changeset ronal:1771998329656-9
ALTER TABLE organization_group_participant
    ADD CONSTRAINT FK_ORGANIZATION_GROUP_PARTICIPANT_MEMBER FOREIGN KEY (organization_member_id) REFERENCES organization_member (id);

-- changeset ronal:1771998329656-10
ALTER TABLE organization_group_participant
    ADD CONSTRAINT FK_ORGANIZATION_GROUP_PARTICIPANT_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organization (id);

-- changeset ronal:1771998329656-1
ALTER TABLE organization
    ALTER COLUMN photo_url SET NOT NULL;

