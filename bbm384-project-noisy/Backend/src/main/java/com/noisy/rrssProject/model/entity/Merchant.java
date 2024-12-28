package com.noisy.rrssProject.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.noisy.rrssProject.model.base.EntityBase;
import com.noisy.rrssProject.model.enums.AccountType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Merchant implements EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private String address;

    private String profilePicture;

    private AccountType accountType;

    @OneToMany
            (mappedBy = "merchant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
