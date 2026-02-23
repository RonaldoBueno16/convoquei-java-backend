package br.com.convoquei.backend.organization.controller;

import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organization.model.enums.OrganizationPermission;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/members")
public class OrganizationMemberController {
    @PostMapping("invite")
    @RequiredOrganizationPermission(OrganizationPermission.INVITE_MEMBERS)
    public void inviteMember(@PathVariable UUID organizationId) {

    }
}
