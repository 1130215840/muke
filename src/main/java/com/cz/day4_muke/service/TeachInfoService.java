package com.cz.day4_muke.service;

import com.cz.day4_muke.domain.TeachInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TeachInfo}.
 */
public interface TeachInfoService {

    /**
     * Save a teachInfo.
     *
     * @param teachInfo the entity to save.
     * @return the persisted entity.
     */
    TeachInfo save(TeachInfo teachInfo);

    /**
     * Get all the teachInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeachInfo> findAll(Pageable pageable);


    /**
     * Get the "id" teachInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeachInfo> findOne(Long id);

    /**
     * Delete the "id" teachInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
