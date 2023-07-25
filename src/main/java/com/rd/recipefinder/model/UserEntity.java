package com.rd.recipefinder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode
@ToString
@Table(name = "user", schema = "users")
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
    @Column(name = "verification_token")
    private String verificationToken;

    public UserEntity(){}

    public UserEntity(String username, String password, String email, Boolean isVerified, String verificationToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isVerified = isVerified;
        this.verificationToken = verificationToken;
    }
}
