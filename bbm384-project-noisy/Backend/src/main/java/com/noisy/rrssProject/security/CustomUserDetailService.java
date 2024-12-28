package com.noisy.rrssProject.security;

import com.noisy.rrssProject.model.entity.Customer;
import com.noisy.rrssProject.service.CustomerService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomUserDetailService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.findCustomerByEmail(email);
        return UserPrincipal.builder()
                .userId(customer.getId())
                .email(customer.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(customer.getRole().toString())))
                .password(customer.getPassword())
                .build();
    }
}
