package br.com.convoquei.backend.organizationInvite.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrganizationInviteDetailsResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("email") String invitedEmail,
        @JsonProperty("organization") String organizationName,
        @JsonProperty("invitedBy") String invitedByUserName,
        @JsonProperty("invitedAt") OffsetDateTime createdAt,
        @JsonProperty("expiresAt") OffsetDateTime expiresAt
) { }
