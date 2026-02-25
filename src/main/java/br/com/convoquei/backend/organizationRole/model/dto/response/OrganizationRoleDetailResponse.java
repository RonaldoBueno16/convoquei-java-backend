package br.com.convoquei.backend.organizationRole.model.dto.response;

import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import br.com.convoquei.backend.organizationRole.model.valueobject.PermissionMask;

import java.util.List;
import java.util.UUID;

public record OrganizationRoleDetailResponse(
        UUID id,
        String name,
        boolean isSystem,
        List<OrganizationPermissionResponse> permissions
) {
    public static OrganizationRoleDetailResponse from(OrganizationRole role) {
        List<OrganizationPermissionResponse> permissions = PermissionMask.fromMask(role.getPermissionsMask())
                .stream()
                .sorted((a, b) -> Long.compare(b.mask(), a.mask()))
                .map(OrganizationPermissionResponse::from)
                .toList();

        return new OrganizationRoleDetailResponse(
                role.getId(),
                role.getName(),
                role.isSystem(),
                permissions
        );
    }
}

