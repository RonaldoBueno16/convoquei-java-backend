package br.com.convoquei.backend.organizationInvite.controller;

import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organizationInvite.model.dto.response.OrganizationInviteDetailsResponse;
import br.com.convoquei.backend.organizationInvite.services.OrganizationUserInviteService;
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
    public PagedResponse<OrganizationInviteDetailsResponse> getUserInvites(@Valid @ModelAttribute PagedRequest request) {
        return organizationUserInviteService.listUserInvites(request);
    }

    @PostMapping("/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptInvite(@RequestParam("inviteId") UUID inviteId) {
        organizationUserInviteService.acceptInvite(inviteId);
    }

    @PostMapping("/decline")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void declineInvite(@RequestParam("inviteId") UUID inviteId) {
        organizationUserInviteService.declineInvite(inviteId);
    }
}
