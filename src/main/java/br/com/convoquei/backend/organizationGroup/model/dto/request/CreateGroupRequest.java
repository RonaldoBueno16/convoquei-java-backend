package br.com.convoquei.backend.organizationGroup.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
        @NotBlank @Size(max = 70) String name,
        @Size(max = 10) String emoji
) {
}

