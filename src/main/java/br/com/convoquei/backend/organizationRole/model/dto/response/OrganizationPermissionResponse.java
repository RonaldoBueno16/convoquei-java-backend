package br.com.convoquei.backend.organizationRole.model.dto.response;

import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;

public record OrganizationPermissionResponse(
        String code,
        String description
) {
    public static OrganizationPermissionResponse from(OrganizationPermission permission) {
        return new OrganizationPermissionResponse(permission.name(), permission.getDescription());
    }
}

