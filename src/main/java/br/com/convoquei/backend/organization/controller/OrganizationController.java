package br.com.convoquei.backend.organization.controller;

import br.com.convoquei.backend.organization.model.dto.request.CreateOrganizationRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization/")
public class OrganizationController {

    @PostMapping
    public void createOrganization(@RequestBody @Valid CreateOrganizationRequest request) {

    }
}
