package com.fastroof.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastroof.security.models.ERole;
import com.fastroof.security.models.Role;

/**
 * The RoleRepository Interface.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	/**
	 * Find role by name.
	 *
	 * @param name the name
	 * @return the role optional
	 */
	Optional<Role> findByName(ERole name);
}
