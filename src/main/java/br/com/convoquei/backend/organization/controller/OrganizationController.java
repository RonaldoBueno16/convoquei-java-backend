package br.com.convoquei.backend.organization.controller;

import br.com.convoquei.backend._shared.model.dto.response.PagedResponse;
import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import br.com.convoquei.backend.organization.model.dto.request.ListOrganizationsByMemberRequest;
import br.com.convoquei.backend.organization.model.dto.response.OrganizationResponse;
import br.com.convoquei.backend.organization.services.CreateOrganizationService;
import br.com.convoquei.backend.organization.services.ListOrganizationByMemberService;
import jakarta.validation.Valid;
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
    public OrganizationResponse createOrganization(@RequestBody @Valid CreateOrganizationRequest request) {
        return createOrganizationService.create(request);
    }

    @GetMapping
    public PagedResponse<OrganizationResponse> listOrganizations(@Valid @ModelAttribute ListOrganizationsByMemberRequest request) {
        return listOrganizationByMemberService.listByMember(request);
    }
}
