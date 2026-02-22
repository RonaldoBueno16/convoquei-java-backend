package br.com.convoquei.backend.user.controller;


import br.com.convoquei.backend.user.model.dto.request.CreateUserWithoutSocialAuthRequest;
import br.com.convoquei.backend.user.model.dto.response.UserResponse;
import br.com.convoquei.backend.user.services.CreateUserWithoutSocialAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserWithoutSocialAuthService createUserWithoutSocialAuthService;

    public UserController(CreateUserWithoutSocialAuthService createUserWithoutSocialAuthService) {
        this.createUserWithoutSocialAuthService = createUserWithoutSocialAuthService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUserWithoutSocialOut(@RequestBody @Valid CreateUserWithoutSocialAuthRequest request) {
        return createUserWithoutSocialAuthService.execute(request);
    }
}