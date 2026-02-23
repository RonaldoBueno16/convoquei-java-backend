package br.com.convoquei.backend.organization.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrganizationRequest(
    @NotBlank @Size(min = 3, max = 70) String name
) { }