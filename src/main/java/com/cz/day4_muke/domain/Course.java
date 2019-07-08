package com.cz.day4_muke.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_info")
    private String courseInfo;

    @Column(name = "course_start_time")
    private ZonedDateTime courseStartTime;

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Teacher teacherCode;

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private CourseType typeCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public Course courseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
        return this;
    }

    public void setCourseInfo(String courseInfo) {
        this.courseInfo = courseInfo;
    }

    public ZonedDateTime getCourseStartTime() {
        return courseStartTime;
    }

    public Course courseStartTime(ZonedDateTime courseStartTime) {
        this.courseStartTime = courseStartTime;
        return this;
    }

    public void setCourseStartTime(ZonedDateTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public Teacher getTeacherCode() {
        return teacherCode;
    }

    public Course teacherCode(Teacher teacher) {
        this.teacherCode = teacher;
        return this;
    }

    public void setTeacherCode(Teacher teacher) {
        this.teacherCode = teacher;
    }

    public CourseType getTypeCode() {
        return typeCode;
    }

    public Course typeCode(CourseType courseType) {
        this.typeCode = courseType;
        return this;
    }

    public void setTypeCode(CourseType courseType) {
        this.typeCode = courseType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseInfo='" + getCourseInfo() + "'" +
            ", courseStartTime='" + getCourseStartTime() + "'" +
            "}";
    }
}
