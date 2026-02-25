package br.com.convoquei.backend.organizationGroup.repository;

import br.com.convoquei.backend._shared.repository.BaseRepository;
import br.com.convoquei.backend.organizationGroup.model.entity.OrganizationGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationGroupRepository extends BaseRepository<OrganizationGroup, UUID> {

    @EntityGraph(attributePaths = {"participants", "participants.organizationMember", "participants.organizationMember.user"})
    Optional<OrganizationGroup> findByIdAndOrganizationId(UUID id, UUID organizationId);

    Page<OrganizationGroup> findAllByOrganizationId(UUID organizationId, Pageable pageable);

    Page<OrganizationGroup> findAllByOrganizationIdAndNameContainingIgnoreCase(UUID organizationId, String name, Pageable pageable);
}


