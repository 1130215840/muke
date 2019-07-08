package com.cz.day4_muke.web.rest;

import com.cz.day4_muke.domain.CourseType;
import com.cz.day4_muke.service.CourseTypeService;
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
 * REST controller for managing {@link com.cz.day4_muke.domain.CourseType}.
 */
@RestController
@RequestMapping("/api")
public class CourseTypeResource {

    private final Logger log = LoggerFactory.getLogger(CourseTypeResource.class);

    private static final String ENTITY_NAME = "courseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseTypeService courseTypeService;

    public CourseTypeResource(CourseTypeService courseTypeService) {
        this.courseTypeService = courseTypeService;
    }

    /**
     * {@code POST  /course-types} : Create a new courseType.
     *
     * @param courseType the courseType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseType, or with status {@code 400 (Bad Request)} if the courseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-types")
    public ResponseEntity<CourseType> createCourseType(@RequestBody CourseType courseType) throws URISyntaxException {
        log.debug("REST request to save CourseType : {}", courseType);
        if (courseType.getId() != null) {
            throw new BadRequestAlertException("A new courseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseType result = courseTypeService.save(courseType);
        return ResponseEntity.created(new URI("/api/course-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-types} : Updates an existing courseType.
     *
     * @param courseType the courseType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseType,
     * or with status {@code 400 (Bad Request)} if the courseType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-types")
    public ResponseEntity<CourseType> updateCourseType(@RequestBody CourseType courseType) throws URISyntaxException {
        log.debug("REST request to update CourseType : {}", courseType);
        if (courseType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseType result = courseTypeService.save(courseType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-types} : get all the courseTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseTypes in body.
     */
    @GetMapping("/course-types")
    public ResponseEntity<List<CourseType>> getAllCourseTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CourseTypes");
        Page<CourseType> page = courseTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-types/:id} : get the "id" courseType.
     *
     * @param id the id of the courseType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-types/{id}")
    public ResponseEntity<CourseType> getCourseType(@PathVariable Long id) {
        log.debug("REST request to get CourseType : {}", id);
        Optional<CourseType> courseType = courseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseType);
    }

    /**
     * {@code DELETE  /course-types/:id} : delete the "id" courseType.
     *
     * @param id the id of the courseType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-types/{id}")
    public ResponseEntity<Void> deleteCourseType(@PathVariable Long id) {
        log.debug("REST request to delete CourseType : {}", id);
        courseTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
