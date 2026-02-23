package br.com.convoquei.backend.user.provider;

import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CurrentUserProvider {
    private final UserRepository userRepository;

    public CurrentUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        String uuid = jwtAuth.getToken().getClaimAsString("uid");
        if (uuid == null) {
            return Optional.empty();
        }

        return userRepository.findById(UUID.fromString(uuid));
    }
}
