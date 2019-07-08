package com.cz.day4_muke.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TeachInfo.
 */
@Entity
@Table(name = "teach_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeachInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("teachInfos")
    private Teacher teacherCode;

    @ManyToOne
    @JsonIgnoreProperties("teachInfos")
    private Course courseCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacherCode() {
        return teacherCode;
    }

    public TeachInfo teacherCode(Teacher teacher) {
        this.teacherCode = teacher;
        return this;
    }

    public void setTeacherCode(Teacher teacher) {
        this.teacherCode = teacher;
    }

    public Course getCourseCode() {
        return courseCode;
    }

    public TeachInfo courseCode(Course course) {
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
        if (!(o instanceof TeachInfo)) {
            return false;
        }
        return id != null && id.equals(((TeachInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TeachInfo{" +
            "id=" + getId() +
            "}";
    }
}
