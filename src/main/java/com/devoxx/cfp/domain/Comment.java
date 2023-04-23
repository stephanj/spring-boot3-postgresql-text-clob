package com.devoxx.cfp.domain;

import com.devoxx.cfp.domain.enumeration.CommentState;
import com.devoxx.cfp.domain.enumeration.ProposalState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "cfp_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "state", nullable = false)
    private CommentState state;

    @Column(name = "description")       // Defined as TEXT in the PostgreSQL database.
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommentState getState() {
        return state;
    }

    public void setState(CommentState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public Comment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return """
            Comment{\
            id=\
            """ + getId() +
            ", state='" + getState() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
