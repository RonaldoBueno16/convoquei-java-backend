package br.com.convoquei.backend.organization.model.entity;

import br.com.convoquei.backend._shared.seedwork.BaseEntity;
import br.com.convoquei.backend.organization.model.enums.OrganizationMemberStatus;
import br.com.convoquei.backend.organization.model.enums.OrganizationPermission;
import br.com.convoquei.backend.user.model.entity.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.EnumSet;

@Entity
@Table(
        name = "organization_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organization_member_organization_user",
                        columnNames = {"organization_id", "user_id"}
                )
        },
        indexes = {
                @Index(name = "idx_organization_member_organization_id", columnList = "organization_id"),
                @Index(name = "idx_organization_member_user_id", columnList = "user_id")
        }
)
public class OrganizationMember extends BaseEntity {

    protected OrganizationMember() {
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_member_organization")
    )
    private Organization organization;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_member_user")
    )
    private User user;

    @Column(name = "permissions_mask")
    private Long permissionsMask = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private OrganizationMemberStatus status;

    @Column(name = "joined_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime joinedAt;

    @Column(name = "left_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime leftAt;

    @Transient
    public EnumSet<OrganizationPermission> getPermissions() {
        long mask = permissionsMask != null ? permissionsMask : 0L;

        EnumSet<OrganizationPermission> permissions = EnumSet.noneOf(OrganizationPermission.class);
        for (OrganizationPermission permission : OrganizationPermission.values()) {
            if ((mask & permission.mask()) != 0) {
                permissions.add(permission);
            }
        }

        return permissions;
    }

    @Override
    protected void onPrePersist() {
        if (permissionsMask == null) {
            permissionsMask = 0L;
        }
    }

    public void setPermissions(EnumSet<OrganizationPermission> permissions) {
        long mask = 0L;
        for (OrganizationPermission permission : permissions) {
            mask |= permission.mask();
        }
        this.permissionsMask = mask;
    }

    public boolean hasPermission(OrganizationPermission permission) {
        long mask = permissionsMask != null ? permissionsMask : 0L;
        return (mask & permission.mask()) != 0;
    }

    public void grantPermission(OrganizationPermission permission) {
        long mask = permissionsMask != null ? permissionsMask : 0L;
        this.permissionsMask = mask | permission.mask();
    }

    public void revokePermission(OrganizationPermission permission) {
        long mask = permissionsMask != null ? permissionsMask : 0L;
        this.permissionsMask = mask & ~permission.mask();
    }
}
