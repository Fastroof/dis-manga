package com.fastroof.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The User Class.
 */
@Getter
@Setter
@Entity
@Table(	name = "users", 
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The role. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	/** The username. */
	@NotBlank
	private String username;

	/** The email. */
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	/** The password. */
	@NotBlank
	@JsonIgnore
	@Size(max = 200)
	private String password;

	/** The provider. */
	@NotBlank
	@Size(max = 200)
	private String provider;

	/**
	 * Instantiates a new user.
	 */
	public User() {
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 * @param email the email
	 * @param password the password
	 */
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
