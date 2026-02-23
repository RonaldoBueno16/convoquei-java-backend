package br.com.convoquei.backend.organization.model.entity;

import br.com.convoquei.backend._shared.seedwork.BaseEntity;
import br.com.convoquei.backend.organization.model.enums.OrganizationStatus;
import br.com.convoquei.backend.user.model.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "organization",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_organization_slug", columnNames = "slug")
        }
)
public class Organization extends BaseEntity {
    protected Organization() { }

    public Organization(User user, String name, String slug) {
        this.name = name;
        this.slug = slug;
        this.status = OrganizationStatus.ACTIVE;

        OrganizationRole ownerRole = OrganizationRole.createOwnerRole(this);
        this.roles.add(ownerRole);

        OrganizationMember member_owner = new OrganizationMember(this, user);
        member_owner.addRole(ownerRole);

        this.members.add(member_owner);
    }

    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @Column(name = "slug", nullable = false, length = 150, unique = true)
    private String slug;

    @Column(name = "photo_url", columnDefinition = "text")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrganizationStatus status;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganizationMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganizationRole> roles = new ArrayList<>();
}
