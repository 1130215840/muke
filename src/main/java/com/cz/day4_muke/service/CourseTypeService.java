package com.cz.day4_muke.service;

import com.cz.day4_muke.domain.CourseType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CourseType}.
 */
public interface CourseTypeService {

    /**
     * Save a courseType.
     *
     * @param courseType the entity to save.
     * @return the persisted entity.
     */
    CourseType save(CourseType courseType);

    /**
     * Get all the courseTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseType> findAll(Pageable pageable);


    /**
     * Get the "id" courseType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseType> findOne(Long id);

    /**
     * Delete the "id" courseType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
