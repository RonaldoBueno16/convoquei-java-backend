package br.com.convoquei.backend.organization.controller;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import br.com.convoquei.backend.organization.model.dto.request.ListOrganizationsByMemberRequest;
import br.com.convoquei.backend.organization.model.dto.response.OrganizationResponse;
import br.com.convoquei.backend.organization.services.CreateOrganizationService;
import br.com.convoquei.backend.organization.services.ListOrganizationByMemberService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations/")
public class OrganizationController {

    private final CreateOrganizationService createOrganizationService;
    private final ListOrganizationByMemberService listOrganizationByMemberService;

    public OrganizationController(CreateOrganizationService createOrganizationService, ListOrganizationByMemberService listOrganizationByMemberService) {
        this.createOrganizationService = createOrganizationService;
        this.listOrganizationByMemberService = listOrganizationByMemberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar organização")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Organização criada com sucesso",
            content = @Content(schema = @Schema(implementation = OrganizationResponse.class)))
    })
    public OrganizationResponse createOrganization(@RequestBody @Valid CreateOrganizationRequest request) {
        return createOrganizationService.create(request);
    }

    @GetMapping
    @Operation(summary = "Listar organizações do membro autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(schema = @Schema(implementation = PagedResponse.class)))
    })
    public PagedResponse<OrganizationResponse> listOrganizations(@Valid @ModelAttribute ListOrganizationsByMemberRequest request) {
        return listOrganizationByMemberService.listByMember(request);
    }
}
