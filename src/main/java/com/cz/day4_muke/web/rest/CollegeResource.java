package com.cz.day4_muke.web.rest;

import com.cz.day4_muke.domain.College;
import com.cz.day4_muke.service.CollegeService;
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
 * REST controller for managing {@link com.cz.day4_muke.domain.College}.
 */
@RestController
@RequestMapping("/api")
public class CollegeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeResource.class);

    private static final String ENTITY_NAME = "college";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegeService collegeService;

    public CollegeResource(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    /**
     * {@code POST  /colleges} : Create a new college.
     *
     * @param college the college to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new college, or with status {@code 400 (Bad Request)} if the college has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colleges")
    public ResponseEntity<College> createCollege(@RequestBody College college) throws URISyntaxException {
        log.debug("REST request to save College : {}", college);
        if (college.getId() != null) {
            throw new BadRequestAlertException("A new college cannot already have an ID", ENTITY_NAME, "idexists");
        }
        College result = collegeService.save(college);
        return ResponseEntity.created(new URI("/api/colleges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colleges} : Updates an existing college.
     *
     * @param college the college to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated college,
     * or with status {@code 400 (Bad Request)} if the college is not valid,
     * or with status {@code 500 (Internal Server Error)} if the college couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colleges")
    public ResponseEntity<College> updateCollege(@RequestBody College college) throws URISyntaxException {
        log.debug("REST request to update College : {}", college);
        if (college.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        College result = collegeService.save(college);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, college.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /colleges} : get all the colleges.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colleges in body.
     */
    @GetMapping("/colleges")
    public ResponseEntity<List<College>> getAllColleges(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Colleges");
        Page<College> page = collegeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colleges/:id} : get the "id" college.
     *
     * @param id the id of the college to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the college, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colleges/{id}")
    public ResponseEntity<College> getCollege(@PathVariable Long id) {
        log.debug("REST request to get College : {}", id);
        Optional<College> college = collegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(college);
    }

    /**
     * {@code DELETE  /colleges/:id} : delete the "id" college.
     *
     * @param id the id of the college to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colleges/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        log.debug("REST request to delete College : {}", id);
        collegeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
