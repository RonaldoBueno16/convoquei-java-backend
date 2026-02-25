package br.com.convoquei.backend.organizationRole.model.entity;

import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMemberRole;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationDefaultSystemRole;
import br.com.convoquei.backend.organizationRole.model.enums.OrganizationPermission;
import br.com.convoquei.backend.organizationRole.model.valueobject.PermissionMask;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.EnumSet;
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

    public static OrganizationRole createCustomRole(Organization organization, String name, EnumSet<OrganizationPermission> permissions) {
        OrganizationRole role = new OrganizationRole();
        role.organization = organization;
        role.name = name;
        role.permissionsMask = PermissionMask.toMask(permissions);
        role.systemKey = null;
        return role;
    }

    public void updatePermissions(EnumSet<OrganizationPermission> permissions) {
        this.permissionsMask = PermissionMask.toMask(permissions);
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
