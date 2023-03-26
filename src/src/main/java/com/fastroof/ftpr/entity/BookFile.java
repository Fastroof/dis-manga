package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "book_files")
public class BookFile {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    private LocalDate createdAt;

    @Column(name = "book_id", nullable = false)
    @JsonProperty("book_id")
    private Integer bookId;

    @Column(name = "link_to_file", nullable = false)
    @JsonProperty("link_to_file")
    private String linkToFile;

    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;
}
