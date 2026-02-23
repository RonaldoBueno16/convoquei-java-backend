package br.com.convoquei.backend.organization.controller;

import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import br.com.convoquei.backend.organization.services.CreateOrganizationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organizations/")
public class OrganizationController {
    private final CreateOrganizationService createOrganizationService;

    public OrganizationController(CreateOrganizationService createOrganizationService) {
        this.createOrganizationService = createOrganizationService;
    }

    @PostMapping
    public void createOrganization(@RequestBody @Valid CreateOrganizationRequest request) {
        createOrganizationService.create(request);
    }
}
