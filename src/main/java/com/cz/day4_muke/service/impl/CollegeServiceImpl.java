package com.cz.day4_muke.service.impl;

import com.cz.day4_muke.service.CollegeService;
import com.cz.day4_muke.domain.College;
import com.cz.day4_muke.repository.CollegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link College}.
 */
@Service
@Transactional
public class CollegeServiceImpl implements CollegeService {

    private final Logger log = LoggerFactory.getLogger(CollegeServiceImpl.class);

    private final CollegeRepository collegeRepository;

    public CollegeServiceImpl(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    /**
     * Save a college.
     *
     * @param college the entity to save.
     * @return the persisted entity.
     */
    @Override
    public College save(College college) {
        log.debug("Request to save College : {}", college);
        return collegeRepository.save(college);
    }

    /**
     * Get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<College> findAll(Pageable pageable) {
        log.debug("Request to get all Colleges");
        return collegeRepository.findAll(pageable);
    }


    /**
     * Get one college by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<College> findOne(Long id) {
        log.debug("Request to get College : {}", id);
        return collegeRepository.findById(id);
    }

    /**
     * Delete the college by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete College : {}", id);
        collegeRepository.deleteById(id);
    }
}
