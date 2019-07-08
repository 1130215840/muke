package com.cz.day4_muke.service.impl;

import com.cz.day4_muke.service.CourseTypeService;
import com.cz.day4_muke.domain.CourseType;
import com.cz.day4_muke.repository.CourseTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseType}.
 */
@Service
@Transactional
public class CourseTypeServiceImpl implements CourseTypeService {

    private final Logger log = LoggerFactory.getLogger(CourseTypeServiceImpl.class);

    private final CourseTypeRepository courseTypeRepository;

    public CourseTypeServiceImpl(CourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
    }

    /**
     * Save a courseType.
     *
     * @param courseType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CourseType save(CourseType courseType) {
        log.debug("Request to save CourseType : {}", courseType);
        return courseTypeRepository.save(courseType);
    }

    /**
     * Get all the courseTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseType> findAll(Pageable pageable) {
        log.debug("Request to get all CourseTypes");
        return courseTypeRepository.findAll(pageable);
    }


    /**
     * Get one courseType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourseType> findOne(Long id) {
        log.debug("Request to get CourseType : {}", id);
        return courseTypeRepository.findById(id);
    }

    /**
     * Delete the courseType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseType : {}", id);
        courseTypeRepository.deleteById(id);
    }
}
