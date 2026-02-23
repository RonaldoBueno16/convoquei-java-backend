package br.com.convoquei.backend.organizationInvite.controller;

import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationInvite.model.dto.request.InviteMemberRequest;
import br.com.convoquei.backend.organizationInvite.model.dto.response.OrganizationInviteResponse;
import br.com.convoquei.backend.organization.model.enums.OrganizationPermission;
import br.com.convoquei.backend.organizationInvite.services.OrganizationInviteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/invites")
public class OrganizationInviteController {
    private final OrganizationInviteService organizationInviteService;

    public OrganizationInviteController(OrganizationInviteService organizationInviteService) {
        this.organizationInviteService = organizationInviteService;
    }

    @PostMapping
    @RequiredOrganizationPermission(OrganizationPermission.INVITE_MEMBERS)
    public OrganizationInviteResponse inviteMember(@PathVariable UUID organizationId, @RequestBody @Valid InviteMemberRequest request) {
        return organizationInviteService.invite(organizationId, request.email());
    }

    @DeleteMapping("/{email}")
    @RequiredOrganizationPermission(OrganizationPermission.INVITE_MEMBERS)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void revokeInvite(@PathVariable UUID organizationId, @PathVariable String email) {
        organizationInviteService.revoke(organizationId, email);
    }
}
