package br.com.convoquei.backend.organizationRole.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrganizationRoleRequest(
        @NotBlank @Size(min = 1, max = 80) String name,
        @NotNull @Size(min = 1) List<String> permissions
) {
}

