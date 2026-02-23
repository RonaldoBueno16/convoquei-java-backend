package br.com.convoquei.backend.user.provider;

import br.com.convoquei.backend.organization.model.entity.OrganizationMember;
import br.com.convoquei.backend.organization.model.enums.OrganizationMemberStatus;
import br.com.convoquei.backend.organization.repository.OrganizationMemberRepository;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CurrentUserProvider {
    private final UserRepository userRepository;
    private final OrganizationMemberRepository organizationMemberRepository;

    public CurrentUserProvider(UserRepository userRepository, OrganizationMemberRepository organizationMemberRepository) {
        this.userRepository = userRepository;
        this.organizationMemberRepository = organizationMemberRepository;
    }

    public Optional<UUID> userId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        String uuid = jwtAuth.getToken().getClaimAsString("uid");
        return Optional.ofNullable(uuid).map(UUID::fromString);
    }

    public Optional<String> email() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        return Optional.ofNullable(jwtAuth.getToken().getClaimAsString("email"));
    }

    public Optional<User> user() {
        UUID userId = extractUserIdFromAuthentication().orElseThrow(() -> new AccessDeniedException("Usuário não autenticado."));

        return userRepository.findById(userId);
    }

    public Optional<OrganizationMember> organizationMembership(UUID organizationId) {
        UUID userId = extractUserIdFromAuthentication().orElseThrow(() -> new AccessDeniedException("Usuário não autenticado."));

        return organizationMemberRepository.findByOrganizationIdAndUserIdAndStatus(organizationId, userId, OrganizationMemberStatus.ACTIVE);
    }

    private Optional<UUID> extractUserIdFromAuthentication() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        String uuid = jwtAuth.getToken().getClaimAsString("uid");
        if (uuid == null) {
            return Optional.empty();
        }

        return Optional.of(UUID.fromString(uuid));
    }
}
