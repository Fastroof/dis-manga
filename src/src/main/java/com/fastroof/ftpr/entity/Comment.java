package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The Comment Class.
 */
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The date when this comment was created. */
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    private LocalDate createdAt;

    /** The text. */
    @Column(name = "text", nullable = false)
    private String text;

    /** The user id. */
    @Column(name = "user_id", nullable = false)
    @JsonProperty("user_id")
    private Integer userId;

    /** The book id. */
    @Column(name = "book_id", nullable = false)
    @JsonProperty("book_id")
    private Integer bookId;
}
