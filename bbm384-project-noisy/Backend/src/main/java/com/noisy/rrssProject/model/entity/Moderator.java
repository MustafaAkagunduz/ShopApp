package com.noisy.rrssProject.model.entity;

import com.noisy.rrssProject.model.base.EntityBase;
import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.model.enums.Gender;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Moderator implements EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled;

    private String name;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private String profilePicture;

    private AccountType accountType;

    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "moderator_community",
            joinColumns = @JoinColumn(name = "moderator_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id"))
    private List<Community> communities = new ArrayList<>();

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
