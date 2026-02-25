package br.com.convoquei.backend.organizationInvite.repository;

import br.com.convoquei.backend._template.repository.BaseRepository;
import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationInviteRepository extends BaseRepository<OrganizationInvite, UUID> {
    boolean existsByOrganizationIdAndInvitedEmail(UUID organizationId, String invitedEmail);
    Optional<OrganizationInvite> findByOrganizationIdAndInvitedEmail(UUID organizationId, String invitedEmail);
}
