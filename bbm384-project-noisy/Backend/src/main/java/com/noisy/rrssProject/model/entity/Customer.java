package com.noisy.rrssProject.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.noisy.rrssProject.model.base.EntityBase;
import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.model.enums.Gender;
import com.noisy.rrssProject.model.enums.UserType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements EntityBase {
    public Customer() {}

    public Customer(Long id, boolean enabled, String name, String username, String email, String password, String phoneNumber, String address, String profilePicture, AccountType accountType, Gender gender, UserType role, List<Product> products, List<Post> posts, List<Reward> rewards, List<Comment> comments) {
        this.id = id;
        this.enabled = enabled;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.profilePicture = profilePicture;
        this.accountType = accountType;
        this.gender = gender;
        this.role = role;
        this.products = products;
        this.posts = posts;
        this.rewards = rewards;
        this.comments = comments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean enabled;

    private String name;

    private String username;

    private String email;

    private String password;
    @Column(nullable = false, length = 11, unique = true, columnDefinition = "CHAR(11)")
    private String phoneNumber;

    private String address;

    private String profilePicture;

    private AccountType accountType;

    private Gender gender;

    private UserType role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_product",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL) // Direkt findBy ile çekip birleştirirken ile alırken bunu kullanıyoruz
    private List<Post> posts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) // service katmanında assign ederken ise bunu. MappedBy'ı incele!!!
    private List<Reward> rewards = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer", cascade=CascadeType.ALL)
    private List<Comment> comments;

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
