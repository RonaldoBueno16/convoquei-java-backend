package br.com.convoquei.backend.organizationGroup.model.entity;

import br.com.convoquei.backend._shared.exceptions.DomainBusinessRuleException;
import br.com.convoquei.backend._shared.model.entity.BaseEntity;
import br.com.convoquei.backend.organization.model.entity.Organization;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organization_group")
public class OrganizationGroup extends BaseEntity {

    protected OrganizationGroup() {
    }

    public OrganizationGroup(Organization organization, String name, String emoji) {
        this.organization = organization;
        this.name = name;
        this.emoji = emoji;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "organization_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_organization_group_organization")
    )
    private Organization organization;

    @Column(name = "name", nullable = false, length = 70)
    private String name;

    @Column(name = "emoji", length = 10)
    private String emoji;

    @OneToMany(mappedBy = "organizationGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrganizationGroupParticipant> participants = new ArrayList<>();

    public void addParticipant(OrganizationMember member) {
        boolean alreadyIn = participants.stream()
                .anyMatch(p -> p.getOrganizationMember().getId().equals(member.getId()));

        if (alreadyIn)
            throw new DomainBusinessRuleException("O membro já pertence a este grupo.");

        this.participants.add(new OrganizationGroupParticipant(this, this.organization, member));
    }

    public void removeParticipant(UUID participantId) {
        boolean removed = participants.removeIf(p -> p.getId().equals(participantId));

        if (!removed)
            throw new DomainBusinessRuleException("Participante não encontrado neste grupo.");
    }

    public void clearParticipants() {
        this.participants.clear();
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public List<OrganizationGroupParticipant> getParticipants() {
        return participants;
    }
}


