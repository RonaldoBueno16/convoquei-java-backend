package br.com.convoquei.backend.organizationRole.model.dto.response;

import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import br.com.convoquei.backend.organizationRole.model.valueobject.PermissionMask;

import java.util.UUID;

public record OrganizationRoleResponse(
        UUID id,
        String name,
        boolean isSystem,
        int permissionCount
) {
    public static OrganizationRoleResponse from(OrganizationRole role) {
        return new OrganizationRoleResponse(
                role.getId(),
                role.getName(),
                role.isSystem(),
                PermissionMask.fromMask(role.getPermissionsMask()).size()
        );
    }
}

