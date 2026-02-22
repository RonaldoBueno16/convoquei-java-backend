package br.com.convoquei.backend.user.services;

import br.com.convoquei.backend.shared.exceptions.DomainConflictException;
import br.com.convoquei.backend.user.model.dto.request.CreateUserWithoutSocialAuthRequest;
import br.com.convoquei.backend.user.model.dto.response.UserResponse;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateUserWithoutSocialAuthService {
    private final UserRepository userRepository;

    public CreateUserWithoutSocialAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse execute(CreateUserWithoutSocialAuthRequest request) {
        if(isEmailAlreadyInUse(request.email()))
            throw new DomainConflictException("Já existe um usuário cadastrado com o e-mail informado.");

        User user = new User(request.name(), request.email(), request.password());
        userRepository.save(user);

        return UserResponse.buildFromUser(user);
    }

    private boolean isEmailAlreadyInUse(String email) {
        return userRepository.existsByEmail(email);
    }
}
