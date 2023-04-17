package com.fastroof.ftpr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fastroof.ftpr.entity.User;

/**
 * The UserRepository Interface.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    /**
     * Find user by email.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);
}
