package com.authenticationApp.authentication.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "authentication")
@Entity
@Data
public class AuthenticationEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Integer isActive = 0;
}
