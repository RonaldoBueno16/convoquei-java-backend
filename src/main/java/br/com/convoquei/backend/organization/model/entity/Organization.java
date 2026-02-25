package br.com.convoquei.backend.organization.model.entity;

import br.com.convoquei.backend._shared.exceptions.DomainBusinessRuleException;
import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.enums.OrganizationStatus;
import br.com.convoquei.backend.organizationInvite.model.entity.OrganizationInvite;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMemberRole;
import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
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
    protected Organization() {
    }

    public Organization(String name, String slug) {
        this.name = name;
        this.slug = slug;
        this.status = OrganizationStatus.ACTIVE;
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

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganizationMemberRole> memberRoles = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationInvite> invites = new ArrayList<>();

    public OrganizationInvite createInvite(OrganizationMember member, String email) {
        if (!member.getOrganization().getId().equals(this.getId()))
            throw new DomainBusinessRuleException("O membro especificado não pertence à organização para criar um convite.");

        OrganizationInvite invite = new OrganizationInvite(this, member, email);

        this.invites.add(invite);

        return invite;
    }

    public void addSystemRoles(List<OrganizationRole> systemRoles) {
        for (OrganizationRole role : systemRoles) {
            if (!role.isSystem())
                throw new DomainBusinessRuleException("A função especificada não é uma função do sistema para ser adicionada.");

            this.roles.add(role);
        }
    }

    public List<OrganizationMember> getMembers() {
        return members;
    }

    public void assignInitialOwner(User user) {
        OrganizationRole ownerRole = this.roles.stream()
                .filter(OrganizationRole::isOwner)
                .findFirst()
                .orElseThrow(() -> new DomainBusinessRuleException("A organização não possui uma função de proprietário definida."));

        OrganizationMember ownerMember = new OrganizationMember(this, user);
        ownerMember.addRole(ownerRole);

        this.members.add(ownerMember);
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public OrganizationStatus getStatus() {
        return status;
    }

}
