package br.com.convoquei.backend.organizationInvite.controller;

import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationInvite.model.dto.request.InviteMemberRequest;
import br.com.convoquei.backend.organizationInvite.model.dto.response.OrganizationInviteResponse;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import br.com.convoquei.backend.organizationInvite.services.OrganizationInviteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Convidar membro para a organização")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Convite enviado com sucesso",
            content = @Content(schema = @Schema(implementation = OrganizationInviteResponse.class)))
    })
    @RequiredOrganizationPermission(OrganizationPermission.INVITE_MEMBERS)
    public OrganizationInviteResponse inviteMember(@PathVariable UUID organizationId, @RequestBody @Valid InviteMemberRequest request) {
        return organizationInviteService.invite(organizationId, request.email());
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Revogar convite de membro da organização")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Convite revogado com sucesso")
    })
    @RequiredOrganizationPermission(OrganizationPermission.INVITE_MEMBERS)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void revokeInvite(@PathVariable UUID organizationId, @PathVariable String email) {
        organizationInviteService.revoke(organizationId, email);
    }
}
