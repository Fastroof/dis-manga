package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.BookFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookFileRepository extends CrudRepository<BookFile, Integer> {
    List<BookFile> findAllByDataSetId(Integer dataSetId);
}
