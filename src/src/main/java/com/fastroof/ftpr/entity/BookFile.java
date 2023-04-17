package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The BookFile Class.
 */
@Getter
@Setter
@Entity
@Table(name = "book_files")
public class BookFile {

    /** The book file id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** The date when this book file was uploaded. */
    @Column(name = "uploaded_at", nullable = false)
    @JsonProperty("uploaded_at")
    private LocalDate uploadedAt;

    /** The book id. */
    @Column(name = "book_id", nullable = false)
    @JsonProperty("book_id")
    private Integer bookId;

    /** The Google Drive id. */
    @Column(name = "google_drive_id", nullable = false)
    @JsonProperty("google_drive_id")
    private String googleDriveId;
}
