package br.com.convoquei.backend.organizationMember.model.entity;

import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import jakarta.persistence.*;

@Entity
@Table(
        name = "organization_member_role",
        uniqueConstraints = {
                @jakarta.persistence.UniqueConstraint(
                        name = "uk_organization_member_role",
                        columnNames = {"organization_member_id", "organization_role_id"}
                )
        }
)
public class OrganizationMemberRole extends BaseEntity {
    protected OrganizationMemberRole() { }

    public OrganizationMemberRole(OrganizationMember organizationMember, OrganizationRole organizationRole) {
        this.organization = organizationRole.getOrganization();
        this.organizationRole = organizationRole;
        this.organizationMember = organizationMember;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_member_role_organization")
    )
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organization_role_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_member_role_role")
    )
    private OrganizationRole organizationRole;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organization_member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_member_role_member")
    )
    private OrganizationMember organizationMember;

    public OrganizationRole getOrganizationRole() {
        return organizationRole;
    }
}
