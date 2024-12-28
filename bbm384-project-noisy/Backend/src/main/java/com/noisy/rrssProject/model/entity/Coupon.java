package com.noisy.rrssProject.model.entity;

import com.noisy.rrssProject.model.base.EntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class Coupon implements EntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code = UUID.randomUUID().toString();

    private int discount;

    private LocalDateTime expiryDate;

    boolean isUsed = false;

    @OneToOne(mappedBy = "coupon", cascade = CascadeType.ALL)
    private Reward reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
