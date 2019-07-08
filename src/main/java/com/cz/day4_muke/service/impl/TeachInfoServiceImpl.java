package com.cz.day4_muke.service.impl;

import com.cz.day4_muke.service.TeachInfoService;
import com.cz.day4_muke.domain.TeachInfo;
import com.cz.day4_muke.repository.TeachInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TeachInfo}.
 */
@Service
@Transactional
public class TeachInfoServiceImpl implements TeachInfoService {

    private final Logger log = LoggerFactory.getLogger(TeachInfoServiceImpl.class);

    private final TeachInfoRepository teachInfoRepository;

    public TeachInfoServiceImpl(TeachInfoRepository teachInfoRepository) {
        this.teachInfoRepository = teachInfoRepository;
    }

    /**
     * Save a teachInfo.
     *
     * @param teachInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TeachInfo save(TeachInfo teachInfo) {
        log.debug("Request to save TeachInfo : {}", teachInfo);
        return teachInfoRepository.save(teachInfo);
    }

    /**
     * Get all the teachInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeachInfo> findAll(Pageable pageable) {
        log.debug("Request to get all TeachInfos");
        return teachInfoRepository.findAll(pageable);
    }


    /**
     * Get one teachInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeachInfo> findOne(Long id) {
        log.debug("Request to get TeachInfo : {}", id);
        return teachInfoRepository.findById(id);
    }

    /**
     * Delete the teachInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeachInfo : {}", id);
        teachInfoRepository.deleteById(id);
    }
}
