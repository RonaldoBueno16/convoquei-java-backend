package br.com.convoquei.backend.organization.repository;

import br.com.convoquei.backend.organization.model.entity.OrganizationMember;
import br.com.convoquei.backend.organization.model.enums.OrganizationMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, UUID> {
    Optional<OrganizationMember> findByOrganizationIdAndUserIdAndStatus(UUID organizationId, UUID userId, OrganizationMemberStatus status);
    boolean existsByOrganizationIdAndUserIdAndStatus(UUID organizationId, UUID userId, OrganizationMemberStatus status);
    boolean existsByOrganizationIdAndUserEmailIgnoreCase(UUID organizationId, String email);
}
