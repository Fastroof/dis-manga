package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.HelpRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The HelpRequestRepository Interface.
 */
@Repository
public interface HelpRequestRepository extends CrudRepository<HelpRequest, Integer> {
}
