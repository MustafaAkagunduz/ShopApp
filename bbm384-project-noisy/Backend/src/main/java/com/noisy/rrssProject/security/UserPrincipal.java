package com.noisy.rrssProject.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String password;

    private UserPrincipal(Long userId, String email, Collection<? extends GrantedAuthority> authorities, String password) {
        this.userId = userId;
        this.email = email;
        this.authorities = authorities;
        this.password = password;
    }

    public static UserPrincipalBuilder builder() {
        return new UserPrincipalBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Assuming email is the username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public static class UserPrincipalBuilder {
        private Long userId;
        private String email;
        private Collection<? extends GrantedAuthority> authorities;
        private String password;

        private UserPrincipalBuilder() {
        }

        public UserPrincipalBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public UserPrincipalBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserPrincipalBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserPrincipalBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserPrincipal build() {
            return new UserPrincipal(userId, email, authorities, password);
        }
    }
}
