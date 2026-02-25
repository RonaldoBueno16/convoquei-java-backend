package br.com.convoquei.backend.organizationGroup.controller;

import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationGroup.model.dto.request.AddGroupParticipantRequest;
import br.com.convoquei.backend.organizationGroup.model.dto.request.CreateGroupRequest;
import br.com.convoquei.backend.organizationGroup.model.dto.request.ListGroupsRequest;
import br.com.convoquei.backend.organizationGroup.model.dto.response.OrganizationGroupParticipantResponse;
import br.com.convoquei.backend.organizationGroup.model.dto.response.OrganizationGroupResponse;
import br.com.convoquei.backend.organizationGroup.services.OrganizationGroupService;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/groups")
public class OrganizationGroupController {

    private final OrganizationGroupService organizationGroupService;

    public OrganizationGroupController(OrganizationGroupService organizationGroupService) {
        this.organizationGroupService = organizationGroupService;
    }

    @GetMapping
    @Operation(summary = "Listar grupos da organização")
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public PagedResponse<OrganizationGroupResponse> listGroups(
            @PathVariable UUID organizationId,
            @Valid @ModelAttribute ListGroupsRequest request
    ) {
        return organizationGroupService.listGroups(organizationId, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar grupo na organização")
    @RequiredOrganizationPermission(OrganizationPermission.MANAGE_GROUPS)
    public OrganizationGroupResponse createGroup(
            @PathVariable UUID organizationId,
            @RequestBody @Valid CreateGroupRequest request
    ) {
        return organizationGroupService.createGroup(organizationId, request);
    }

    @GetMapping("/{groupId}/participants")
    @Operation(summary = "Listar participantes do grupo")
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public PagedResponse<OrganizationGroupParticipantResponse> getParticipants(
            @PathVariable UUID organizationId,
            @PathVariable UUID groupId,
            @Valid @ModelAttribute PagedRequest request
    ) {
        return organizationGroupService.getParticipants(organizationId, groupId, request);
    }

    @PostMapping("/{groupId}/participants")
    @Operation(summary = "Adicionar participante no grupo")
    @RequiredOrganizationPermission(OrganizationPermission.MANAGE_GROUPS)
    public OrganizationGroupResponse addParticipant(
            @PathVariable UUID organizationId,
            @PathVariable UUID groupId,
            @RequestBody @Valid AddGroupParticipantRequest request
    ) {
        return organizationGroupService.addParticipant(organizationId, groupId, request);
    }

    @DeleteMapping("/{groupId}/participants/{participantId}")
    @Operation(summary = "Remover participante do grupo")
    @RequiredOrganizationPermission(OrganizationPermission.MANAGE_GROUPS)
    public OrganizationGroupResponse removeParticipant(
            @PathVariable UUID organizationId,
            @PathVariable UUID groupId,
            @PathVariable UUID participantId
    ) {
        return organizationGroupService.removeParticipant(organizationId, groupId, participantId);
    }

    @DeleteMapping("/{groupId}/participants")
    @Operation(summary = "Remover todos os participantes do grupo")
    @RequiredOrganizationPermission(OrganizationPermission.MANAGE_GROUPS)
    public OrganizationGroupResponse clearGroup(
            @PathVariable UUID organizationId,
            @PathVariable UUID groupId
    ) {
        return organizationGroupService.clearGroup(organizationId, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir grupo da organização")
    @RequiredOrganizationPermission(OrganizationPermission.MANAGE_GROUPS)
    public void deleteGroup(
            @PathVariable UUID organizationId,
            @PathVariable UUID groupId
    ) {
        organizationGroupService.deleteGroup(organizationId, groupId);
    }
}



