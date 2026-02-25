package br.com.convoquei.backend.organizationMember.model.dto.response;

import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.user.model.entity.User;

import java.util.List;

public record MyMemberhipResponse(
        String name,
        String avatarUrl,
        String status,
        boolean isOwner,
        List<OrganizationMemberPermissionResponse> permissions
) {
    public static MyMemberhipResponse fromMember(OrganizationMember member) {
        User user = member.getUser();

        List<OrganizationMemberPermissionResponse> permissions = member.getPermissions().stream()
                .map(OrganizationMemberPermissionResponse::buildFromPermission)
                .toList();

        return new MyMemberhipResponse(user.getName(), user.getAvatarUrl(), member.getStatus().name(), member.isOwner(), permissions);
    }
}
