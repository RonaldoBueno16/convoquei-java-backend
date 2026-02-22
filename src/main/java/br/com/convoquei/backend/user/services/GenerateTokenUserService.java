package br.com.convoquei.backend.user.services;

import br.com.convoquei.backend._shared.config.jwt.service.JwtService;
import br.com.convoquei.backend.user.model.dto.response.TokenResponse;
import br.com.convoquei.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class GenerateTokenUserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public GenerateTokenUserService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public TokenResponse generate(UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar o usuário para gerar o token."));

        var token = jwtService.generateAccessToken(user.getId(), userDetails);
        var refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

        return new TokenResponse(token, refreshToken);
    }
}
