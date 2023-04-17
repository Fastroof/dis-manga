package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The Book Class.
 */
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    /** The book id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The date when this book was last updated. */
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    private LocalDate updatedAt;

    /** The date when this book was created. */
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    private LocalDate createdAt;

    /** The tag id. */
    @Column(name = "tag_id")
    @JsonProperty("tag_id")
    private Integer tagId;

    /** The name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** The owner id. */
    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;

    /** The link to cover. */
    @Column(name = "link_to_cover")
    @JsonProperty("link_to_cover")
    private String linkToCover;
}
