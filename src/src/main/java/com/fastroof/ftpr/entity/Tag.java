package com.fastroof.ftpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The Tag Class.
 */
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The name. */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** The main tag. */
    @Column(name = "main_tag")
    private Integer mainTag;
}
