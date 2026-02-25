package br.com.convoquei.backend.organizationRole.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public enum OrganizationDefaultSystemRole {

    OWNER("OWNER", "Dono da Organizacao", OrganizationPermission.OWNER),
    MEMBER("MEMBER", "Membro da Organizacao", OrganizationPermission.MEMBER);

    private final String systemKey;
    private final String displayName;
    private final EnumSet<OrganizationPermission> permissions;

    OrganizationDefaultSystemRole(String systemKey, String displayName, OrganizationPermission... permissions) {
        this.systemKey = systemKey;
        this.displayName = displayName;

        if (permissions == null || permissions.length == 0) {
            this.permissions = EnumSet.noneOf(OrganizationPermission.class);
        } else {
            this.permissions = EnumSet.of(permissions[0], permissions);
        }
    }

    public String getSystemKey() { return systemKey; }

    public String getDisplayName() { return displayName; }

    public EnumSet<OrganizationPermission> getPermissions() {
        return EnumSet.copyOf(permissions);
    }

    public long getPermissionsMask() {
        long mask = 0L;
        for (var p : permissions) mask |= p.mask();
        return mask;
    }

    public boolean hasPermission(OrganizationPermission permission) {
        return permissions.contains(permission);
    }
}