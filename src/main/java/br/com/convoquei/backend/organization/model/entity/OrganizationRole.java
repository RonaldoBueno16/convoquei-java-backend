package br.com.convoquei.backend.organization.model.entity;

import br.com.convoquei.backend._shared.seedwork.BaseEntity;
import br.com.convoquei.backend.organization.model.enums.OrganizationPermission;
import jakarta.persistence.*;

@Entity
@Table(name = "organization_role")
public class OrganizationRole extends BaseEntity {
    protected OrganizationRole() { }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_role_organization")
    )
    private Organization organization;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "permissions_mask", nullable = false)
    private Long permissionsMask = 0L;

    @Column(name = "is_system", nullable = false)
    private boolean isSystem;

    public static OrganizationRole createOwnerRole(Organization organization) {
        OrganizationRole ownerRole = new OrganizationRole();
        ownerRole.organization = organization;
        ownerRole.name = "Dono da Organização";
        ownerRole.permissionsMask = OrganizationPermission.OWNER.mask();
        ownerRole.isSystem = true;
        return ownerRole;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Long getPermissionsMask() {
        return permissionsMask;
    }
}
