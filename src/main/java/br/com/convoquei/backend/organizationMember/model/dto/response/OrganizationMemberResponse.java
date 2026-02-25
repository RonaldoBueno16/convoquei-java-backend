package br.com.convoquei.backend.organizationMember.model.dto.response;

import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.enums.OrganizationMemberStatus;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public record OrganizationMemberResponse(
        UUID id,
        String userName,
        String userAvatarUrl,
        OrganizationMemberStatus status,
        OffsetDateTime joinedAt,
        OffsetDateTime leftAt,
        List<OrganizationMemberPermissionResponse> permissions
) {
    public static OrganizationMemberResponse buildFromMember(OrganizationMember member) {
        List<OrganizationMemberPermissionResponse> permissions = member.getPermissions().stream()
                .sorted(Comparator.comparingLong(OrganizationPermission::mask).reversed())
                .map(OrganizationMemberPermissionResponse::buildFromPermission)
                .toList();

        return new OrganizationMemberResponse(
                member.getId(),
                member.getUser().getName(),
                member.getUser().getAvatarUrl(),
                member.getStatus(),
                member.getJoinedAt(),
                member.getLeftAt(),
                permissions
        );
    }
}
