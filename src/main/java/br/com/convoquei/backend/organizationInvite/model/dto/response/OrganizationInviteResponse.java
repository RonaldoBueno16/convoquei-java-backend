package br.com.convoquei.backend.organizationInvite.model.dto.response;

import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;

public record OrganizationInviteResponse(
        String email
) {
    public static OrganizationInviteResponse buildFromInvite(OrganizationInvite invite) {
        return new OrganizationInviteResponse(invite.getInvitedEmail());
    }
}
