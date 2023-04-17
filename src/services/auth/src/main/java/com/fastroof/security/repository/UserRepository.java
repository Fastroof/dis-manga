package com.fastroof.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastroof.security.models.User;

/**
 * The UserRepository Interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Checks if user exists by email.
	 *
	 * @param email the email
	 * @return true, if user exists
	 */
	Boolean existsByEmail(String email);

    /**
     * Find user by email.
     *
     * @param username the username
     * @return the user optional
     */
    Optional<User> findByEmail(String username);
}
