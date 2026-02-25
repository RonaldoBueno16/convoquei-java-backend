package br.com.convoquei.backend.organizationGroup.repository;

import br.com.convoquei.backend._shared.repository.BaseRepository;
import br.com.convoquei.backend.organizationGroup.model.entity.OrganizationGroupParticipant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.UUID;

public interface OrganizationGroupParticipantRepository extends BaseRepository<OrganizationGroupParticipant, UUID> {

    void deleteAllByOrganizationMemberId(UUID organizationMemberId);

    @EntityGraph(attributePaths = {"organizationMember", "organizationMember.user"})
    Page<OrganizationGroupParticipant> findAllByOrganizationGroupIdAndOrganizationId(UUID organizationGroupId, UUID organizationId, Pageable pageable);
}

