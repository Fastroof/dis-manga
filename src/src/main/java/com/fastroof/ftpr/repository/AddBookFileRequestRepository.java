package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.AddBookFileRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddBookFileRequestRepository extends CrudRepository<AddBookFileRequest, Integer> {
    List<AddBookFileRequest> findAllByStatus(int i);
}
