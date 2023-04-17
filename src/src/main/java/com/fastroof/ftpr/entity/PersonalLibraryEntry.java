package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The PersonalLibraryEntry Class.
 */
@Getter
@Setter
@Entity
@Table(name = "personal_libraries")
public class PersonalLibraryEntry {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;
    
    /** The user id. */
    @Column(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private Integer userId;

    /** The book id. */
    @Column(name = "book_id", nullable = false)
    @JsonProperty("book_id")
    private Integer bookId;
}
