package br.com.convoquei.backend.organizationRole.model.enums;

public enum OrganizationPermission {

    OWNER(0, "Dono da Organizacao"),// 1
    MEMBER(1, "Permissao para acessar recursos da organizacao"), //2
    INVITE_MEMBERS(2, "Permissao para convidar membros"), // 4
    KICK_MEMBERS(3, "Permissao para remover membros"); // 8

    private final int bitIndex;
    private final String description;

    OrganizationPermission(int bitIndex, String description) {
        this.bitIndex = bitIndex;
        this.description = description;
    }

    public boolean isOwner() { return this == OWNER; }

    public long mask() { return 1L << bitIndex; }

    public String getDescription() { return description; }
}