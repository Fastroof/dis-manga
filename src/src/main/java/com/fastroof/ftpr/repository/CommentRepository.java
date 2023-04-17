package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The CommentRepository Interface.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    
    /**
     * Find all comments by book id.
     *
     * @param bookId the book id
     * @return the list of comments
     */
    List<Comment> findAllByBookId(Integer bookId);
}
