package br.com.convoquei.backend.organizationMember.repository;

import br.com.convoquei.backend._template.repository.BaseRepository;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.enums.OrganizationMemberStatus;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationMemberRepository extends BaseRepository<OrganizationMember, UUID> {
    @EntityGraph(attributePaths = {"user", "roles", "roles.organizationRole"})
    Optional<OrganizationMember> findByOrganizationIdAndUserIdAndStatus(UUID organizationId, UUID userId, OrganizationMemberStatus status);
    boolean existsByOrganizationIdAndUserIdAndStatus(UUID organizationId, UUID userId, OrganizationMemberStatus status);
    boolean existsByOrganizationIdAndUserEmailIgnoreCase(UUID organizationId, String email);
}
