package br.com.convoquei.backend.organization.services;

import br.com.convoquei.backend._shared.exceptions.DomainConflictException;
import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import br.com.convoquei.backend.organization.model.dto.response.OrganizationResponse;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organization.repository.OrganizationRepository;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final SlugOrganizationService slugOrganizationService;
    private final CurrentUserProvider currentUserProvider;

    public CreateOrganizationService(OrganizationRepository organizationRepository, SlugOrganizationService slugOrganizationService, CurrentUserProvider currentUserProvider) {
        this.organizationRepository = organizationRepository;
        this.slugOrganizationService = slugOrganizationService;
        this.currentUserProvider = currentUserProvider;
    }

    @Transactional
    public OrganizationResponse create(CreateOrganizationRequest request) {
        var slug = slugOrganizationService.generate(request.name());
        if (!slugOrganizationService.isUnique(slug)) {
            throw new DomainConflictException("Já existe uma organização com esse nome.");
        }

        User currentUser = currentUserProvider.requireUser();

        Organization organization = new Organization(request.name(), slug, currentUser);

        organizationRepository.save(organization);

        return OrganizationResponse.buildFromOrganization(organization);
    }
}
