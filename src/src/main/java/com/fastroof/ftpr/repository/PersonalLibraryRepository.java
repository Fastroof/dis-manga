package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.PersonalLibraryEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The PersonalLibraryRepository Interface.
 */
@Repository
public interface PersonalLibraryRepository extends CrudRepository<PersonalLibraryEntry, Integer> {
    
    /**
     * Gets all personal library entries by user id.
     *
     * @param userId the user id
     * @return the list of personal library entries
     */
    List<PersonalLibraryEntry> getAllByUserId(Integer userId);
    
    /**
     * Gets the personal library entry by user id and book id.
     *
     * @param userId the user id
     * @param bookId the book id
     * @return the personal library entry
     */
    PersonalLibraryEntry getByUserIdAndBookId(Integer userId, Integer bookId);
}
