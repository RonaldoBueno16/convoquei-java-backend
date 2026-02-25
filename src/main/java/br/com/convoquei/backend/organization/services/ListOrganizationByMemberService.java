package br.com.convoquei.backend.organization.services;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organization.model.dto.request.ListOrganizationsByMemberRequest;
import br.com.convoquei.backend.organization.model.dto.response.OrganizationResponse;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organization.repository.OrganizationRepository;
import br.com.convoquei.backend.organization.repository.specifications.OrganizationSpec;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListOrganizationByMemberService {

    private final CurrentUserProvider currentUserProvider;
    private final OrganizationRepository organizationRepository;

    public ListOrganizationByMemberService(CurrentUserProvider currentUserProvider, OrganizationRepository organizationRepository) {
        this.currentUserProvider = currentUserProvider;
        this.organizationRepository = organizationRepository;
    }

    public PagedResponse<OrganizationResponse> listByMember(ListOrganizationsByMemberRequest request) {
        Specification<Organization> specification = genetareSpecification(request);

        var page = organizationRepository.findAll(specification, request.toPageRequest())
                .map(OrganizationResponse::buildFromOrganization);

        return PagedResponse.buildFromPage(page);
    }

    private Specification<Organization> genetareSpecification(ListOrganizationsByMemberRequest request) {
        UUID userId = currentUserProvider.requireUserId();

        Specification<Organization> specification = OrganizationSpec.memberUserId(userId);

        specification = specification.and(OrganizationSpec.from(request));

        return specification;
    }
}
