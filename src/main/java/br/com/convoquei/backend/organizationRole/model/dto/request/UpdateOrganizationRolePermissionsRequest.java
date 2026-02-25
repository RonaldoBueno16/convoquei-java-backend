package br.com.convoquei.backend.organizationRole.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateOrganizationRolePermissionsRequest(
        @NotNull List<String> permissions
) {
}

