package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.EditBookFileRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditBookFileRequestRepository extends CrudRepository<EditBookFileRequest, Integer> {
    List<EditBookFileRequest> findAllByStatus(int i);
}
