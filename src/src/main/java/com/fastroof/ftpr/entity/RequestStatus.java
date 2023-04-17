package com.fastroof.ftpr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * The RequestStatus Class.
 */
@Getter
@Setter
@Entity
@Table(name = "request_statuses")
public class RequestStatus {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The name. */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
