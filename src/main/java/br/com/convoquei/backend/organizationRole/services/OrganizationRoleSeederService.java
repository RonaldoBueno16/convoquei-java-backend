package br.com.convoquei.backend.organizationRole.services;

import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationDefaultSystemRole;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrganizationRoleSeederService {
    public void assignDefaultSystemRoles(Organization organization) {
        List<OrganizationRole> defaultSystemRoles = Arrays.stream(OrganizationDefaultSystemRole.values())
                .map(r -> OrganizationRole.createSystemRole(organization, r))
                .toList();

        organization.addSystemRoles(defaultSystemRoles);
    }
}
