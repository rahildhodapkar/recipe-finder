package com.rd.recipefinder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode
@ToString
@Table(name = "user_info")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Basic
    @Column(name = "email_verification_token")
    private String emailVerificationToken;
    @Basic
    @Column(name = "pword_verification_token")
    private String pwordVerificationToken;

    public UserEntity(){}

    public UserEntity(String username, String password, String email, Boolean isVerified, String emailVerificationToken,
                      String pwordVerificationToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.emailVerificationToken = emailVerificationToken;
        this.pwordVerificationToken = pwordVerificationToken;
    }
}
