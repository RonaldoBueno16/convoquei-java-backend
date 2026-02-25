package br.com.convoquei.backend.organizationMember.services;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organizationMember.model.dto.request.ListMembersByOrganizationRequest;
import br.com.convoquei.backend.organizationMember.model.dto.response.MyMemberhipResponse;
import br.com.convoquei.backend.organizationMember.model.dto.response.OrganizationMemberResponse;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.repository.OrganizationMemberRepository;
import br.com.convoquei.backend.organizationMember.repository.specifications.OrganizationMemberSpec;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationMemberService {

    private final CurrentUserProvider currentUserProvider;
    private final OrganizationMemberRepository organizationMemberRepository;

    public OrganizationMemberService(CurrentUserProvider currentUserProvider, OrganizationMemberRepository organizationMemberRepository) {
        this.currentUserProvider = currentUserProvider;
        this.organizationMemberRepository = organizationMemberRepository;
    }

    public MyMemberhipResponse getMembership(UUID organizationId) {
        OrganizationMember member = currentUserProvider.organizationMembership(organizationId)
                .orElseThrow(() -> new AccessDeniedException("Você não é membro desta organização."));

        return MyMemberhipResponse.fromMember(member);
    }

    public PagedResponse<OrganizationMemberResponse> listMembers(UUID organizationId, ListMembersByOrganizationRequest request) {
        Specification<OrganizationMember> spec = OrganizationMemberSpec.fetchUserAndRoles()
                .and(OrganizationMemberSpec.organizationId(organizationId))
                .and(OrganizationMemberSpec.from(request));

        var page = organizationMemberRepository.findAll(spec, request.toPageRequest(Sort.by("joinedAt").ascending()))
                .map(OrganizationMemberResponse::buildFromMember);

        return PagedResponse.buildFromPage(page);
    }
}
