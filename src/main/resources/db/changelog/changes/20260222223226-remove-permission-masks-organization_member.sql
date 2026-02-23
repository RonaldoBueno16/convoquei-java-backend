-- liquibase formatted sql

-- changeset ronal:1771810346820-3
ALTER TABLE organization_member
    DROP COLUMN permissions_mask;

