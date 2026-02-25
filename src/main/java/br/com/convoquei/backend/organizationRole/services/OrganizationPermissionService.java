package br.com.convoquei.backend.organizationRole.services;

import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.enums.OrganizationMemberStatus;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import br.com.convoquei.backend.organizationMember.repository.OrganizationMemberRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationPermissionService {
    private final OrganizationMemberRepository organizationMemberRepository;

    public OrganizationPermissionService(OrganizationMemberRepository organizationMemberRepository) {
        this.organizationMemberRepository = organizationMemberRepository;
    }

    public void assertHasPermission(UUID organizationId, UUID userId, OrganizationPermission requiredPermission) throws AccessDeniedException {
        OrganizationMember member = organizationMemberRepository
                .findByOrganizationIdAndUserIdAndStatus(organizationId, userId, OrganizationMemberStatus.ACTIVE)
                .orElseThrow(() -> new AccessDeniedException("Você não é um membro ativo da organização."));

        if(!member.hasPermission(requiredPermission)) {
            throw new AccessDeniedException("Requer " + requiredPermission.getDescription().toLowerCase());
        }
    }
}
