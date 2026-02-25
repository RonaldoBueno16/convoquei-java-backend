package br.com.convoquei.backend.organizationMember.services;

import br.com.convoquei.backend._shared.exceptions.DomainBusinessRuleException;
import br.com.convoquei.backend._shared.exceptions.DomainNotFoundException;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organizationGroup.repository.OrganizationGroupParticipantRepository;
import br.com.convoquei.backend.organizationMember.model.dto.request.ListMembersByOrganizationRequest;
import br.com.convoquei.backend.organizationMember.model.dto.response.MyMemberhipResponse;
import br.com.convoquei.backend.organizationMember.model.dto.response.OrganizationMemberResponse;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.enums.OrganizationMemberStatus;
import br.com.convoquei.backend.organizationMember.repository.OrganizationMemberRepository;
import br.com.convoquei.backend.organizationMember.repository.specifications.OrganizationMemberSpec;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import br.com.convoquei.backend.user.provider.CurrentUserProvider;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrganizationMemberService {

    private final CurrentUserProvider currentUserProvider;
    private final OrganizationMemberRepository organizationMemberRepository;
    private final OrganizationGroupParticipantRepository organizationGroupParticipantRepository;

    public OrganizationMemberService(CurrentUserProvider currentUserProvider, OrganizationMemberRepository organizationMemberRepository, OrganizationGroupParticipantRepository organizationGroupParticipantRepository) {
        this.currentUserProvider = currentUserProvider;
        this.organizationMemberRepository = organizationMemberRepository;
        this.organizationGroupParticipantRepository = organizationGroupParticipantRepository;
    }

    public MyMemberhipResponse getMembership(UUID organizationId) {
        OrganizationMember member = currentUserProvider.requireOrganizationMembership(organizationId);

        return MyMemberhipResponse.fromMember(member);
    }

    @Transactional
    public OrganizationMemberResponse kickMemberOfOrganization(UUID organizationId, UUID memberId) {
        OrganizationMember memberAdmin = currentUserProvider.requireOrganizationMembership(organizationId);
        if (!memberAdmin.hasPermission(OrganizationPermission.KICK_MEMBERS))
            throw new DomainBusinessRuleException("Você não tem permissão para expulsar membros dessa organização.");

        OrganizationMember memberToKick = organizationMemberRepository.findByIdAndOrganizationIdAndStatus(memberId, organizationId, OrganizationMemberStatus.ACTIVE)
                .orElseThrow(() -> new DomainNotFoundException("Membro não encontrado nessa organização."));

        if (memberToKick.getId().equals(memberAdmin.getId())) {
            if (memberToKick.isOwner())
                throw new DomainBusinessRuleException("Você não pode abandonar a organização enquanto for o dono. Transfira a propriedade ou exclua a organização para sair.");

            throw new DomainBusinessRuleException("Você não pode se expulsar da organização, ao em vez disso, abandone-a.");
        }

        if (memberToKick.isOwner())
            throw new DomainBusinessRuleException("O dono da organização não pode ser expulso.");

        memberToKick.markAsAbandoned();

        organizationGroupParticipantRepository.deleteAllByOrganizationMemberId(memberToKick.getId());
        organizationMemberRepository.save(memberToKick);

        return OrganizationMemberResponse.buildFromMember(memberToKick);
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
