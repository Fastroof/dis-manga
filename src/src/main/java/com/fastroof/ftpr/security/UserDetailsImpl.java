package com.fastroof.ftpr.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fastroof.ftpr.service.auth.UserDetailsDeserializer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * The UserDetailsImpl Class.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonDeserialize(using = UserDetailsDeserializer.class)
public class UserDetailsImpl implements UserDetails {
    
    /** The id. */
    private Long id;
    
    /** The username. */
    private String username;
    
    /** The email. */
    private String email;

    /** The password. */
    @JsonIgnore
    private String password;

    /** The authorities. */
    private Collection<GrantedAuthority> authorities = new ArrayList<>();

    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
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
        return username;
    }

    /**
     * Checks if account is not expired.
     *
     * @return true, if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if is account is not locked.
     *
     * @return true, if account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if credentials are not expired.
     *
     * @return true, if credentials are not expired
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
