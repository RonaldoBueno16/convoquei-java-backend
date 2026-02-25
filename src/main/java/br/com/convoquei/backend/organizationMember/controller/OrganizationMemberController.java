package br.com.convoquei.backend.organizationMember.controller;

import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationMember.model.dto.response.MyMemberhipResponse;
import br.com.convoquei.backend.organizationMember.services.OrganizationMemberService;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/members")
public class OrganizationMemberController {

    private final OrganizationMemberService organizationMemberService;

    public OrganizationMemberController(OrganizationMemberService organizationMemberService) {
        this.organizationMemberService = organizationMemberService;
    }

    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    @GetMapping("/me")
    public MyMemberhipResponse getMembership(@PathVariable UUID organizationId) {
        return organizationMemberService.getMembership(organizationId);
    }
}
