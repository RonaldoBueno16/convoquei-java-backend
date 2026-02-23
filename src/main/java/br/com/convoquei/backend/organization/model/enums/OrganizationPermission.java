package br.com.convoquei.backend.organization.model.enums;

public enum OrganizationPermission {

    OWNER(-1, "Dono da Organização"),
    INVITE_MEMBERS(1, "Permissão para convidar membros"),
    MANAGE_MEMBERS(2, "Permissão para gerenciar membros"),
    MANAGE_ORGANIZATION(3, "Permissão para gerenciar a organização");

    private final int bitIndex;
    private final String description;

    OrganizationPermission(int bitIndex, String description) {
        this.bitIndex = bitIndex;
        this.description = description;
    }

    public boolean isOwner() {
        return this == OWNER;
    }

    public long mask() {
        return 1L << bitIndex;
    }

    public String getDescription() {
        return description;
    }
}