package br.com.convoquei.backend.organization.model.dto.response;

import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organization.model.enums.OrganizationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrganizationResponse(
        UUID id,
        String name,
        String slug,
        String photoUrl,
        OrganizationStatus status,
        int totalMembers,
        OffsetDateTime createdAt
) {
    public static OrganizationResponse buildFromOrganization(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getSlug(),
                organization.getPhotoUrl(),
                organization.getStatus(),
                organization.getMembers().size(),
                organization.getCreatedAt()
        );
    }
}
