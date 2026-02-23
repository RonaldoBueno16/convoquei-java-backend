package br.com.convoquei.backend.organizationInvite.model.entity;

import br.com.convoquei.backend._shared.seedwork.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organization.model.entity.OrganizationMember;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "organization_invite",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organization_invite_organization_email",
                        columnNames = {"organization_id", "invited_email"}
                )
        }
)
public class OrganizationInvite extends BaseEntity {
    protected OrganizationInvite() {}

    public OrganizationInvite(Organization organization, OrganizationMember memberInvite, String invitedEmail) {
        this.organization = organization;
        this.invitedEmail = invitedEmail;
        this.expiresAt = OffsetDateTime.now().plusDays(7);
        this.invitedBy = memberInvite;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_invite_organization")
    )
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "invited_by",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_invite_invited_by_member")
    )
    private OrganizationMember invitedBy;

    @Column(name = "invited_email", length = 150, nullable = false)
    private String invitedEmail;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    public String getInvitedEmail() {
        return invitedEmail;
    }
}
