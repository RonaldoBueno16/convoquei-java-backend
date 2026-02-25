-- liquibase formatted sql

-- changeset ronal:1771875261579-3
ALTER TABLE organization_role
    ADD system_key VARCHAR(50);

-- changeset ronal:1771875261579-4
ALTER TABLE organization_role
    DROP COLUMN is_system;

