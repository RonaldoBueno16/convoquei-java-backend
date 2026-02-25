package br.com.convoquei.backend.organizationMember.repository.specifications;

import br.com.convoquei.backend._shared.helpers.SpecHelper;
import br.com.convoquei.backend.organizationMember.model.dto.request.ListMembersByOrganizationRequest;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMember;
import br.com.convoquei.backend.organizationMember.model.entity.OrganizationMemberRole;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class OrganizationMemberSpec {
    private OrganizationMemberSpec() { }

    public static Specification<OrganizationMember> organizationId(UUID organizationId) {
        return (root, query, cb) -> cb.equal(root.get("organization").get("id"), organizationId);
    }

    public static Specification<OrganizationMember> from(ListMembersByOrganizationRequest request) {
        Specification<OrganizationMember> spec = alwaysTrue();

        if (SpecHelper.hasText(request.getName())) {
            spec = spec.and((root, query, cb) -> {
                var user = root.join("user");
                return SpecHelper.<OrganizationMember>startsWithIgnoreCase(user, "name", request.getName().trim())
                        .toPredicate(root, query, cb);
            });
        }

        if (request.getStatus() != null) {
            spec = spec.and(SpecHelper.equalEnum("status", request.getStatus()));
        }

        if (request.getRoleId() != null) {
            spec = spec.and((root, query, cb) -> {
                var roles = root.join("roles");
                var organizationRole = roles.join("organizationRole");
                return cb.equal(organizationRole.get("id"), request.getRoleId());
            });
        }

        return spec;
    }

    public static Specification<OrganizationMember> fetchUserAndRoles() {
        return (root, query, cb) -> {
            if (!query.getResultType().equals(Long.class)) {
                root.fetch("user", JoinType.LEFT);
                var roles = root.<OrganizationMember, OrganizationMemberRole>fetch("roles", JoinType.LEFT);
                roles.fetch("organizationRole", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }

    private static Specification<OrganizationMember> alwaysTrue() {
        return (root, query, cb) -> cb.conjunction();
    }
}

