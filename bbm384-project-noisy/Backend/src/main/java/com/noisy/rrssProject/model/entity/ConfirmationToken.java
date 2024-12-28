package com.noisy.rrssProject.model.entity;

import com.noisy.rrssProject.model.dto.response.PasswordResponse;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    public <T> ConfirmationToken(T entity) {
        if(entity instanceof Customer){
            this.customer = (Customer) entity;
        }else if(entity instanceof Moderator) {
            this.moderator = (Moderator) entity;
        }else if(entity instanceof PasswordResponse){
            this.customer = ((PasswordResponse) entity).getCustomer();
        }
        else {
            this.merchant = (Merchant) entity;
        }
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
    public ConfirmationToken() {}



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private Long tokenId;


    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(targetEntity = Moderator.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "moderator_id")
    private Moderator moderator;

    @OneToOne(targetEntity = Merchant.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Moderator getModerator() {
        return moderator;
    }

    public void setModerator(Moderator moderator) {
        this.moderator = moderator;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}

