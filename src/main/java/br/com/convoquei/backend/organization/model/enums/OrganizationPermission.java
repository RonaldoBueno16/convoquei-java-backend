package br.com.convoquei.backend.organization.model.enums;

public enum OrganizationPermission {
    INVITE_MEMBERS(0),
    MANAGE_MEMBERS(1),
    MANAGE_ORGANIZATION(2);

    private final int bitIndex;

    OrganizationPermission(int bitIndex) {
        this.bitIndex = bitIndex;
    }

    public long mask() {
        return 1L << bitIndex;
    }
}