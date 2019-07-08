package com.cz.day4_muke.service;

import com.cz.day4_muke.domain.College;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link College}.
 */
public interface CollegeService {

    /**
     * Save a college.
     *
     * @param college the entity to save.
     * @return the persisted entity.
     */
    College save(College college);

    /**
     * Get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<College> findAll(Pageable pageable);


    /**
     * Get the "id" college.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<College> findOne(Long id);

    /**
     * Delete the "id" college.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
