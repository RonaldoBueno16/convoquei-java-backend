package br.com.convoquei.backend.organizationGroup.model.dto.response;

import br.com.convoquei.backend.organizationGroup.model.entity.OrganizationGroupParticipant;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrganizationGroupParticipantResponse(
        UUID id,
        UUID memberId,
        String memberName,
        String memberAvatarUrl,
        OffsetDateTime joinedAt
) {
    public static OrganizationGroupParticipantResponse buildFromParticipant(OrganizationGroupParticipant participant) {
        return new OrganizationGroupParticipantResponse(
                participant.getId(),
                participant.getOrganizationMember().getId(),
                participant.getOrganizationMember().getUser().getName(),
                participant.getOrganizationMember().getUser().getAvatarUrl(),
                participant.getJoinedAt()
        );
    }
}

