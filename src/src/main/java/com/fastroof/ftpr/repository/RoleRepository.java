package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The RoleRepository Interface.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
