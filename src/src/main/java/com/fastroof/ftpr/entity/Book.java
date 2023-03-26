package com.fastroof.ftpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Column(name = "link_to_cover")
    private String linkToCover;
}
