package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The TagRepository Interface.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
