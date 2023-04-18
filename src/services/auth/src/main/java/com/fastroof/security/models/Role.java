package com.fastroof.security.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The Role Class.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
	
	/** The id. */
	@Id
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	@GeneratedValue(generator = "increment")
	private Long id;

	/** The name. */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}