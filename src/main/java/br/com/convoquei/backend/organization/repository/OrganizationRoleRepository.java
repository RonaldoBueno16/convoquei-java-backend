package br.com.convoquei.backend.organization.repository;

import br.com.convoquei.backend._shared.repository.BaseRepository;
import br.com.convoquei.backend.organizationRole.model.entity.OrganizationRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrganizationRoleRepository extends BaseRepository<OrganizationRole, UUID> {
    @Query("""
        SELECT omr.organizationRole FROM OrganizationMemberRole omr
        WHERE omr.organizationMember.id = :memberId
    """)
    List<OrganizationRole> findRolesByOrganizationMemberId(@Param("memberId") UUID memberId);
}
