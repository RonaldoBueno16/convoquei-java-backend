package br.com.convoquei.backend.organizationRole.controller;

import br.com.convoquei.backend._shared.model.dto.request.PagedRequest;
import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationRole.model.dto.request.CreateOrganizationRoleRequest;
import br.com.convoquei.backend.organizationRole.model.dto.request.UpdateOrganizationRolePermissionsRequest;
import br.com.convoquei.backend.organizationRole.model.dto.response.OrganizationPermissionResponse;
import br.com.convoquei.backend.organizationRole.model.dto.response.OrganizationRoleDetailResponse;
import br.com.convoquei.backend.organizationRole.model.dto.response.OrganizationRoleResponse;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import br.com.convoquei.backend.organizationRole.services.OrganizationRoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/roles")
public class OrganizationRoleController {

    private final OrganizationRoleService organizationRoleService;

    public OrganizationRoleController(OrganizationRoleService organizationRoleService) {
        this.organizationRoleService = organizationRoleService;
    }

    @GetMapping("/permissions")
    @Operation(summary = "Listar permissões disponíveis da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public List<OrganizationPermissionResponse> listAvailablePermissions(@PathVariable UUID organizationId) {
        return organizationRoleService.listAvailablePermissions();
    }

    @GetMapping
    @Operation(summary = "Listar cargos da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public PagedResponse<OrganizationRoleResponse> listRoles(
            @PathVariable UUID organizationId,
            @Valid @ModelAttribute PagedRequest request) {
        return organizationRoleService.listRoles(organizationId, request);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Obter detalhes de um cargo da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public OrganizationRoleDetailResponse getRole(
            @PathVariable UUID organizationId,
            @PathVariable UUID roleId) {
        return organizationRoleService.getRole(organizationId, roleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar cargo na organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public OrganizationRoleDetailResponse createRole(
            @PathVariable UUID organizationId,
            @RequestBody @Valid CreateOrganizationRoleRequest request) {
        return organizationRoleService.createRole(organizationId, request);
    }

    @PatchMapping("/{roleId}/permissions")
    @Operation(summary = "Atualizar permissões de um cargo da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public OrganizationRoleDetailResponse updateRolePermissions(
            @PathVariable UUID organizationId,
            @PathVariable UUID roleId,
            @RequestBody @Valid UpdateOrganizationRolePermissionsRequest request) {
        return organizationRoleService.updateRolePermissions(organizationId, roleId, request);
    }

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir cargo da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public void deleteRole(
            @PathVariable UUID organizationId,
            @PathVariable UUID roleId) {
        organizationRoleService.deleteRole(organizationId, roleId);
    }

    @PostMapping("/{roleId}/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Atribuir cargo a um membro da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public void assignRole(
            @PathVariable UUID organizationId,
            @PathVariable UUID roleId,
            @PathVariable UUID memberId) {
        organizationRoleService.assignRole(organizationId, roleId, memberId);
    }

    @DeleteMapping("/{roleId}/members/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover cargo de um membro da organização")
    @RequiredOrganizationPermission(OrganizationPermission.OWNER)
    public void unassignRole(
            @PathVariable UUID organizationId,
            @PathVariable UUID roleId,
            @PathVariable UUID memberId) {
        organizationRoleService.unassignRole(organizationId, roleId, memberId);
    }
}
