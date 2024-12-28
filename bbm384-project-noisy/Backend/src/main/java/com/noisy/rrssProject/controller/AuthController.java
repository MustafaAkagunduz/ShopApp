package com.noisy.rrssProject.controller;

import com.noisy.rrssProject.model.entity.LoginRequest;
import com.noisy.rrssProject.model.entity.LoginResponse;
import com.noisy.rrssProject.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        return authService.attemptLogin(request.getEmail(),request.getPassword());
    }



}
