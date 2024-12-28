package com.noisy.rrssProject.service;

import com.noisy.rrssProject.security.JwtIssuer;
import com.noisy.rrssProject.security.UserPrincipal;
import com.noisy.rrssProject.model.entity.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public AuthService(JwtIssuer jwtIssuer, AuthenticationManager authenticationManager) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse attemptLogin(String email, String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtIssuer.issue(principal.getUserId(),principal.getEmail(), roles);
        return new LoginResponse(token);


    }
}
