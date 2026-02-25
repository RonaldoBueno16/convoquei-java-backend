package br.com.convoquei.backend.organizationRole.model.entity;

import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMemberRole;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationDefaultSystemRole;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "system_key", nullable = true, length = 50)
    private String systemKey;

    @OneToMany(mappedBy = "organizationRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganizationMemberRole> memberRoles = new ArrayList<>();

    public static OrganizationRole createSystemRole(Organization organization, OrganizationDefaultSystemRole defaultRole) {
        OrganizationRole role = new OrganizationRole();
        role.organization = organization;
        role.name = defaultRole.getDisplayName();
        role.permissionsMask = defaultRole.getPermissionsMask();
        role.systemKey = defaultRole.getSystemKey();
        return role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Long getPermissionsMask() {
        return permissionsMask;
    }

    public boolean isSystem() {
        return systemKey != null;
    }

    public boolean isOwner() {
        return OrganizationDefaultSystemRole.OWNER.getSystemKey().equals(this.systemKey);
    }

    public String getName() {
        return name;
    }

    public String getSystemKey() {
        return systemKey;
    }
}
