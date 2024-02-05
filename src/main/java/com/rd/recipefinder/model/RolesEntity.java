package com.rd.recipefinder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "role_info")
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "role")
    private String role;

    public RolesEntity(){}

    public RolesEntity(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
