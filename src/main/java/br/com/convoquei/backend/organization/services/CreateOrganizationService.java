package br.com.convoquei.backend.organization.services;

import br.com.convoquei.backend._shared.exceptions.DomainBusinessRuleException;
import br.com.convoquei.backend._shared.exceptions.DomainConflictException;
import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organization.repository.OrganizationRepository;
import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import br.com.convoquei.backend.organizationRole.services.OrganizationRoleSeederService;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SlugOrganizationService slugOrganizationService;
    private final CurrentUserProvider currentUserProvider;
    private final OrganizationRoleSeederService organizationRoleSeederService;

    public CreateOrganizationService(OrganizationRepository organizationRepository, SlugOrganizationService slugOrganizationService, CurrentUserProvider currentUserProvider, OrganizationRoleSeederService organizationRoleSeederService) {
        this.organizationRepository = organizationRepository;
        this.slugOrganizationService = slugOrganizationService;
        this.currentUserProvider = currentUserProvider;
        this.organizationRoleSeederService = organizationRoleSeederService;
    }

    @Transactional
    public void create(CreateOrganizationRequest request) {
        var slug = slugOrganizationService.generate(request.name());
        if (!slugOrganizationService.isUnique(slug)) {
            throw new DomainConflictException("Já existe uma organização com esse nome.");
        }

        User currentUser = currentUserProvider.user();

        Organization organization = new Organization(request.name(), slug);

        organizationRoleSeederService.assignDefaultSystemRoles(organization);
        organization.assignInitialOwner(currentUser);

        organizationRepository.save(organization);
    }
}
