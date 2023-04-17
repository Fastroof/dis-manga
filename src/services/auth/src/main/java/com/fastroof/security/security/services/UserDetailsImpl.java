package com.fastroof.security.security.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fastroof.security.models.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The UserDetailsImpl Class.
 */
@ToString
public class UserDetailsImpl implements UserDetails {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private final Long id;

	/** The username. */
	private final String username;

	/** The email. */
	private final String email;

	/** The password. */
	@JsonIgnore
	private final String password;

	/** The authorities. */
	private final Collection<? extends GrantedAuthority> authorities;

	/**
	 * Instantiates a new user details impl.
	 *
	 * @param id the id
	 * @param username the username
	 * @param email the email
	 * @param password the password
	 * @param authorities the authorities
	 */
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/**
	 * Builds the user details.
	 *
	 * @param user the user
	 * @return the user details
	 */
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName().name()));

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	/**
	 * Gets the authorities.
	 *
	 * @return the authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the pib.
	 *
	 * @return the pib
	 */
	public String getPIB() {
		return username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@Override
	public String getUsername() {
		return email;
	}

	/**
	 * Checks if is account non expired.
	 *
	 * @return true, if is account non expired
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Checks if the account is not locked.
	 *
	 * @return true, if the account is not locked
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Checks if credentials are not expired.
	 *
	 * @return true, if credentials are not expired.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Checks if is enabled.
	 *
	 * @return true, if is enabled
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Equals.
	 *
	 * @param o the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
