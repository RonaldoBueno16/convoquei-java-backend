package br.com.convoquei.backend.organization.repository;

import br.com.convoquei.backend._shared.repository.BaseRepository;
import br.com.convoquei.backend.organization.model.entity.Organization;

import java.util.UUID;

public interface OrganizationRepository extends BaseRepository<Organization, UUID> {
    public boolean existsBySlug(String slug);
}
