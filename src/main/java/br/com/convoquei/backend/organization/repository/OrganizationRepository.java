package br.com.convoquei.backend.organization.repository;

import br.com.convoquei.backend.organization.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface OrganizationRepository extends JpaRepository<Organization, UUID> {
}
