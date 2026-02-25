package br.com.convoquei.backend.organizationRole.model.valueobject;

import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;

import java.util.EnumSet;

public final class PermissionMask {
    private PermissionMask() {}

    public static long toMask(EnumSet<OrganizationPermission> set) {
        if (set == null || set.isEmpty()) return 0L;

        long mask = 0L;
        for (var p : set) mask |= p.mask();
        return mask;
    }

    public static EnumSet<OrganizationPermission> fromMask(Long dbMask) {
        long mask = (dbMask == null) ? 0L : dbMask;

        var set = EnumSet.noneOf(OrganizationPermission.class);
        for (var p : OrganizationPermission.values()) {
            if ((mask & p.mask()) != 0) set.add(p);
        }
        return set;
    }
}