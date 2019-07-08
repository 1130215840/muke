package com.cz.day4_muke.web.rest;

import com.cz.day4_muke.domain.TeachInfo;
import com.cz.day4_muke.service.TeachInfoService;
import com.cz.day4_muke.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cz.day4_muke.domain.TeachInfo}.
 */
@RestController
@RequestMapping("/api")
public class TeachInfoResource {

    private final Logger log = LoggerFactory.getLogger(TeachInfoResource.class);

    private static final String ENTITY_NAME = "teachInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachInfoService teachInfoService;

    public TeachInfoResource(TeachInfoService teachInfoService) {
        this.teachInfoService = teachInfoService;
    }

    /**
     * {@code POST  /teach-infos} : Create a new teachInfo.
     *
     * @param teachInfo the teachInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachInfo, or with status {@code 400 (Bad Request)} if the teachInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teach-infos")
    public ResponseEntity<TeachInfo> createTeachInfo(@RequestBody TeachInfo teachInfo) throws URISyntaxException {
        log.debug("REST request to save TeachInfo : {}", teachInfo);
        if (teachInfo.getId() != null) {
            throw new BadRequestAlertException("A new teachInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachInfo result = teachInfoService.save(teachInfo);
        return ResponseEntity.created(new URI("/api/teach-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teach-infos} : Updates an existing teachInfo.
     *
     * @param teachInfo the teachInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachInfo,
     * or with status {@code 400 (Bad Request)} if the teachInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teach-infos")
    public ResponseEntity<TeachInfo> updateTeachInfo(@RequestBody TeachInfo teachInfo) throws URISyntaxException {
        log.debug("REST request to update TeachInfo : {}", teachInfo);
        if (teachInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeachInfo result = teachInfoService.save(teachInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teachInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teach-infos} : get all the teachInfos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachInfos in body.
     */
    @GetMapping("/teach-infos")
    public ResponseEntity<List<TeachInfo>> getAllTeachInfos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TeachInfos");
        Page<TeachInfo> page = teachInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teach-infos/:id} : get the "id" teachInfo.
     *
     * @param id the id of the teachInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teach-infos/{id}")
    public ResponseEntity<TeachInfo> getTeachInfo(@PathVariable Long id) {
        log.debug("REST request to get TeachInfo : {}", id);
        Optional<TeachInfo> teachInfo = teachInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachInfo);
    }

    /**
     * {@code DELETE  /teach-infos/:id} : delete the "id" teachInfo.
     *
     * @param id the id of the teachInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teach-infos/{id}")
    public ResponseEntity<Void> deleteTeachInfo(@PathVariable Long id) {
        log.debug("REST request to delete TeachInfo : {}", id);
        teachInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
