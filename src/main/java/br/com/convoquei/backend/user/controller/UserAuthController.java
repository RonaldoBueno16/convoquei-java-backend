package br.com.convoquei.backend.user.controller;


import br.com.convoquei.backend.user.model.dto.request.CreateUserRequest;
import br.com.convoquei.backend.user.model.dto.response.UserResponse;
import br.com.convoquei.backend.user.services.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserAuthController {

    private final CreateUserService CreateUserService;

    public UserAuthController(CreateUserService CreateUserService) {
        this.CreateUserService = CreateUserService;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return CreateUserService.execute(request);
    }
}