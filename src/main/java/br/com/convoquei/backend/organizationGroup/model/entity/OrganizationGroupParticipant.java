package br.com.convoquei.backend.organizationGroup.model.entity;

import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "organization_group_participant",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organization_group_participant",
                        columnNames = {"organization_group_id", "organization_member_id"}
                )
        }
)
public class OrganizationGroupParticipant extends BaseEntity {

    protected OrganizationGroupParticipant() {
    }

    public OrganizationGroupParticipant(OrganizationGroup organizationGroup, Organization organization, OrganizationMember member) {
        this.organizationGroup = organizationGroup;
        this.organization = organization;
        this.organizationMember = member;
        this.joinedAt = OffsetDateTime.now();
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_group_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_group_participant_group")
    )
    private OrganizationGroup organizationGroup;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_group_participant_organization")
    )
    private Organization organization;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_group_participant_member")
    )
    private OrganizationMember organizationMember;

    @Column(name = "joined_at", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime joinedAt;

    public OrganizationGroup getOrganizationGroup() {
        return organizationGroup;
    }

    public Organization getOrganization() {
        return organization;
    }

    public OrganizationMember getOrganizationMember() {
        return organizationMember;
    }

    public OffsetDateTime getJoinedAt() {
        return joinedAt;
    }
}

