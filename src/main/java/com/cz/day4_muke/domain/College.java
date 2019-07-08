package com.cz.day4_muke.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A College.
 */
@Entity
@Table(name = "college")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class College implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "college_info")
    private String collegeInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public College collegeName(String collegeName) {
        this.collegeName = collegeName;
        return this;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeInfo() {
        return collegeInfo;
    }

    public College collegeInfo(String collegeInfo) {
        this.collegeInfo = collegeInfo;
        return this;
    }

    public void setCollegeInfo(String collegeInfo) {
        this.collegeInfo = collegeInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof College)) {
            return false;
        }
        return id != null && id.equals(((College) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "College{" +
            "id=" + getId() +
            ", collegeName='" + getCollegeName() + "'" +
            ", collegeInfo='" + getCollegeInfo() + "'" +
            "}";
    }
}
