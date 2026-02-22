package br.com.convoquei.backend.user.controller;


import br.com.convoquei.backend.user.model.dto.request.CreateUserRequest;
import br.com.convoquei.backend.user.model.dto.request.LoginRequest;
import br.com.convoquei.backend.user.model.dto.response.TokenResponse;
import br.com.convoquei.backend.user.model.dto.response.UserResponse;
import br.com.convoquei.backend.user.services.GenerateTokenUserService;
import br.com.convoquei.backend.user.services.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserAuthController {

    private final CreateUserService CreateUserService;
    private final AuthenticationManager authenticationManager;
    private final GenerateTokenUserService generateTokenUserService;

    public UserAuthController(CreateUserService CreateUserService, AuthenticationManager authenticationManager, GenerateTokenUserService generateTokenUserService) {
        this.CreateUserService = CreateUserService;
        this.authenticationManager = authenticationManager;
        this.generateTokenUserService = generateTokenUserService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return CreateUserService.execute(request);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = (UserDetails) auth.getPrincipal();
        if (user == null) {
            throw new RuntimeException("Não foi possível seguir com o login.");
        }

        return generateTokenUserService.generate(user);
    }
}