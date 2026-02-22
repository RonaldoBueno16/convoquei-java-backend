package br.com.convoquei.backend.user.services;

import br.com.convoquei.backend._shared.exceptions.DomainConflictException;
import br.com.convoquei.backend.user.model.dto.request.CreateUserRequest;
import br.com.convoquei.backend.user.model.dto.response.UserResponse;
import br.com.convoquei.backend.user.model.entity.User;
import br.com.convoquei.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse execute(CreateUserRequest request) {
        if(isEmailAlreadyInUse(request.email()))
            throw new DomainConflictException("Já existe um usuário cadastrado com o e-mail informado.");

        User user = new User(request.name(), request.email(), passwordEncoder.encode(request.password()));
        userRepository.save(user);

        return UserResponse.buildFromUser(user);
    }

    private boolean isEmailAlreadyInUse(String email) {
        return userRepository.existsByEmail(email);
    }
}
