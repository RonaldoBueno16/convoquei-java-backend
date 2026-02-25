package br.com.convoquei.backend.organizationInvite.model.dto.response;

import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;

import java.time.OffsetDateTime;

public record OrganizationInviteResponse(
        String email,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt
) {
    public static OrganizationInviteResponse buildFromInvite(OrganizationInvite invite) {
        return new OrganizationInviteResponse(invite.getInvitedEmail(), invite.getCreatedAt(), invite.getExpiresAt());
    }
}
