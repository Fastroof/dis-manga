package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The BookRepository Interface.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    
    /**
     * Gets the books if name contains query.
     *
     * @param query the query
     * @return the list of books
     */
    List<Book> getBooksByNameContains(String query);
    
    /**
     * Find books by tag id.
     *
     * @param tagId the tag id
     * @return the list of books
     */
    List<Book> findAllByTagId(Integer tagId);
    
    /**
     * Gets the books by owner id.
     *
     * @param ownerId the owner id
     * @return the list of books
     */
    List<Book> getBooksByOwnerId(Integer ownerId);
}
