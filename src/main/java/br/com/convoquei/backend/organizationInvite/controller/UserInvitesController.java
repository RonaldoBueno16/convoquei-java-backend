package br.com.convoquei.backend.organizationInvite.controller;

import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organizationInvite.model.dto.response.OrganizationInviteDetailsResponse;
import br.com.convoquei.backend.organizationInvite.services.OrganizationUserInviteService;
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
@RequestMapping("/api/users/me/invites")
public class UserInvitesController {

    private final OrganizationUserInviteService organizationUserInviteService;

    public UserInvitesController(OrganizationUserInviteService organizationUserInviteService) {
        this.organizationUserInviteService = organizationUserInviteService;
    }

    @GetMapping
    @Operation(summary = "Listar convites do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
    })
    public PagedResponse<OrganizationInviteDetailsResponse> getUserInvites(@Valid @ModelAttribute PagedRequest request) {
        return organizationUserInviteService.listUserInvites(request);
    }

    @PostMapping("/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Aceitar convite de organização")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Convite aceito com sucesso")
    })
    public void acceptInvite(@RequestParam("inviteId") UUID inviteId) {
        organizationUserInviteService.acceptInvite(inviteId);
    }

    @PostMapping("/decline")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Recusar convite de organização")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Convite recusado com sucesso")
    })
    public void declineInvite(@RequestParam("inviteId") UUID inviteId) {
        organizationUserInviteService.declineInvite(inviteId);
    }
}
