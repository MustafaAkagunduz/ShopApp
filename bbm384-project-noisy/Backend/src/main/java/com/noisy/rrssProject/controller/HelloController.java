package com.noisy.rrssProject.controller;

import com.noisy.rrssProject.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, world! This is public to everyone";
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Everyone can see this";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal principal){
        return "If you're a user who logged in, your ID "+ principal.getUserId() +
                " and your email is : "+principal.getEmail();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal){
        return "If you're a admin who logged in, your ID "+ principal.getUserId() +
                " and your email is : "+principal.getEmail();
    }
}
