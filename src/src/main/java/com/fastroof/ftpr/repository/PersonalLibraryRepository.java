package com.fastroof.ftpr.repository;

import com.fastroof.ftpr.entity.PersonalLibraryEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalLibraryRepository extends CrudRepository<PersonalLibraryEntry, Integer> {
    List<PersonalLibraryEntry> getAllByUserId(Integer userId);
}
