package br.com.convoquei.backend.organizationInvite.repository;

import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationInviteRepository extends JpaRepository<OrganizationInvite, UUID> {
    boolean existsByOrganizationIdAndInvitedEmail(UUID organizationId, String invitedEmail);
    Optional<OrganizationInvite> findByOrganizationIdAndInvitedEmail(UUID organizationId, String invitedEmail);
}
