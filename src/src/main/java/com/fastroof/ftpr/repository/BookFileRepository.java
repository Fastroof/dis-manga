package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.BookFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The BookFileRepository Interface.
 */
@Repository
public interface BookFileRepository extends CrudRepository<BookFile, Integer> {
    
    /**
     * Find book files by book id.
     *
     * @param bookId the book id
     * @return the list of book files
     */
    List<BookFile> findAllByBookId(Integer bookId);
    
    /**
     * Find book file by id and book id.
     *
     * @param id the id
     * @param bookId the book id
     * @return the book file
     */
    BookFile findByIdAndBookId(Integer id, Integer bookId);
}
