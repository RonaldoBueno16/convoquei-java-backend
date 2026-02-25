package br.com.convoquei.backend.organizationGroup.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddGroupParticipantRequest(
        @NotNull UUID memberId
) {
}

