package com.fastroof.ftpr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * The HelpRequest Class.
 */
@Getter
@Setter
@Entity
@Table(name = "help_requests")
public class HelpRequest {
    
    /** The id. */
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(generator = "increment")
    private Integer id;

    /** The date when this help request was created. */
    @Column(name = "created_at", nullable = false)
    @JsonProperty("created_at")
    private LocalDate createdAt;

    /** The text. */
    @Column(name = "text", nullable = false)
    private String text;

    /** The email. */
    @Column(name = "email", nullable = false)
    private String email;

    /** The status. */
    @Column(name = "status", nullable = false)
    private Integer status;

    /** The moderator id. */
    @Column(name = "moderator_id")
    @JsonProperty("moderator_id")
    private Integer moderatorId;
}
