package br.com.convoquei.backend.organizationMember.controller;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationMember.model.dto.request.ListMembersByOrganizationRequest;
import br.com.convoquei.backend.organizationMember.model.dto.response.MyMemberhipResponse;
import br.com.convoquei.backend.organizationMember.model.dto.response.OrganizationMemberResponse;
import br.com.convoquei.backend.organizationMember.services.OrganizationMemberService;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/members")
public class OrganizationMemberController {

    private final OrganizationMemberService organizationMemberService;

    public OrganizationMemberController(OrganizationMemberService organizationMemberService) {
        this.organizationMemberService = organizationMemberService;
    }

    @GetMapping("/me")
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public MyMemberhipResponse getMembership(@PathVariable UUID organizationId) {
        return organizationMemberService.getMembership(organizationId);
    }

    @GetMapping
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public PagedResponse<OrganizationMemberResponse> listMembers(@PathVariable UUID organizationId, @Valid @ModelAttribute ListMembersByOrganizationRequest request) {
        return organizationMemberService.listMembers(organizationId, request);
    }

    @DeleteMapping("/{memberId}")
    @RequiredOrganizationPermission(OrganizationPermission.KICK_MEMBERS)
    public OrganizationMemberResponse kickMemberOfOrganization(@PathVariable UUID organizationId, @PathVariable UUID memberId) {
        return organizationMemberService.kickMemberOfOrganization(organizationId, memberId);
    }

}
