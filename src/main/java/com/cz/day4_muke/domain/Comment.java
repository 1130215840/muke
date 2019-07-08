package com.cz.day4_muke.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "comment_time")
    private ZonedDateTime commentTime;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Student studentCode;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Course courseCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Comment commentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public ZonedDateTime getCommentTime() {
        return commentTime;
    }

    public Comment commentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
        return this;
    }

    public void setCommentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public Student getStudentCode() {
        return studentCode;
    }

    public Comment studentCode(Student student) {
        this.studentCode = student;
        return this;
    }

    public void setStudentCode(Student student) {
        this.studentCode = student;
    }

    public Course getCourseCode() {
        return courseCode;
    }

    public Comment courseCode(Course course) {
        this.courseCode = course;
        return this;
    }

    public void setCourseCode(Course course) {
        this.courseCode = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", commentContent='" + getCommentContent() + "'" +
            ", commentTime='" + getCommentTime() + "'" +
            "}";
    }
}
