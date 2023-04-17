package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.RequestStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The RequestStatusRepository Interface.
 */
@Repository
public interface RequestStatusRepository extends CrudRepository<RequestStatus, Integer> {
}
