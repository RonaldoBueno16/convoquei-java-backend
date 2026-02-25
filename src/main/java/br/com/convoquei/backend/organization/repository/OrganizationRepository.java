package br.com.convoquei.backend.organization.repository;

import br.com.convoquei.backend._template.repository.BaseRepository;
import br.com.convoquei.backend.organization.model.dto.response.OrganizationResponse;
import br.com.convoquei.backend.organization.model.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends BaseRepository<Organization, UUID> {
    public boolean existsBySlug(String slug);
}
