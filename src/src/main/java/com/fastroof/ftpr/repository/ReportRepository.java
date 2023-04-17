package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface ReportRepository.
 */
@Repository
public interface ReportRepository extends CrudRepository<Report, Integer>{
}