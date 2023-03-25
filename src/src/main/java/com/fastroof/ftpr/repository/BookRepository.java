package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> getDataSetsByNameContains(String name);
    List<Book> findAllByTagId(Integer tagId);
}
