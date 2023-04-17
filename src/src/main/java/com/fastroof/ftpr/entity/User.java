package com.fastroof.ftpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The User Class.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    /** The id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The role. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    /** The username. */
    @Column(name = "username", nullable = false)
    private String username;

    /** The email. */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** The password. */
    @Column(name = "password", nullable = false)
    private String password;

    /** The provider. */
    @Column(name = "provider", nullable = false)
    private String provider;
}
