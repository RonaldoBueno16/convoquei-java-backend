package br.com.convoquei.backend.organizationMember.controller;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend._shared.security.annotation.RequiredOrganizationPermission;
import br.com.convoquei.backend.organizationMember.model.dto.request.ListMembersByOrganizationRequest;
import br.com.convoquei.backend.organizationMember.model.dto.response.MyMemberhipResponse;
import br.com.convoquei.backend.organizationMember.model.dto.response.OrganizationMemberResponse;
import br.com.convoquei.backend.organizationMember.services.OrganizationMemberService;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Obter dados de membro da organização do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso",
            content = @Content(schema = @Schema(implementation = MyMemberhipResponse.class)))
    })
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public MyMemberhipResponse getMembership(@PathVariable UUID organizationId) {
        return organizationMemberService.getMembership(organizationId);
    }

    @GetMapping
    @Operation(summary = "Listar membros da organização")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
    })
    @RequiredOrganizationPermission(OrganizationPermission.MEMBER)
    public PagedResponse<OrganizationMemberResponse> listMembers(@PathVariable UUID organizationId, @Valid @ModelAttribute ListMembersByOrganizationRequest request) {
        return organizationMemberService.listMembers(organizationId, request);
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "Remover membro da organização")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Membro removido com sucesso",
            content = @Content(schema = @Schema(implementation = OrganizationMemberResponse.class)))
    })
    @RequiredOrganizationPermission(OrganizationPermission.KICK_MEMBERS)
    public OrganizationMemberResponse kickMemberOfOrganization(@PathVariable UUID organizationId, @PathVariable UUID memberId) {
        return organizationMemberService.kickMemberOfOrganization(organizationId, memberId);
    }

}
