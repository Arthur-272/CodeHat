package com.example.demo.EmailConfirmation;

import com.example.demo.Users.Users;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="EmailConfirmation")
public class EmailConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    private Users user;

    public EmailConfirmation(){}
    public EmailConfirmation(Users user){
        this.user = user;
        date = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
