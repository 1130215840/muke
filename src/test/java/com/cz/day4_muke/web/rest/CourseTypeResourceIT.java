package com.cz.day4_muke.web.rest;

import com.cz.day4_muke.Day4MukeApp;
import com.cz.day4_muke.domain.CourseType;
import com.cz.day4_muke.repository.CourseTypeRepository;
import com.cz.day4_muke.service.CourseTypeService;
import com.cz.day4_muke.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cz.day4_muke.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CourseTypeResource} REST controller.
 */
@SpringBootTest(classes = Day4MukeApp.class)
public class CourseTypeResourceIT {

    private static final String DEFAULT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private CourseTypeService courseTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCourseTypeMockMvc;

    private CourseType courseType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseTypeResource courseTypeResource = new CourseTypeResource(courseTypeService);
        this.restCourseTypeMockMvc = MockMvcBuilders.standaloneSetup(courseTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseType createEntity(EntityManager em) {
        CourseType courseType = new CourseType()
            .typeName(DEFAULT_TYPE_NAME);
        return courseType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseType createUpdatedEntity(EntityManager em) {
        CourseType courseType = new CourseType()
            .typeName(UPDATED_TYPE_NAME);
        return courseType;
    }

    @BeforeEach
    public void initTest() {
        courseType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourseType() throws Exception {
        int databaseSizeBeforeCreate = courseTypeRepository.findAll().size();

        // Create the CourseType
        restCourseTypeMockMvc.perform(post("/api/course-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseType)))
            .andExpect(status().isCreated());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTypeName()).isEqualTo(DEFAULT_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createCourseTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseTypeRepository.findAll().size();

        // Create the CourseType with an existing ID
        courseType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseTypeMockMvc.perform(post("/api/course-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseType)))
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourseTypes() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get all the courseTypeList
        restCourseTypeMockMvc.perform(get("/api/course-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeName").value(hasItem(DEFAULT_TYPE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCourseType() throws Exception {
        // Initialize the database
        courseTypeRepository.saveAndFlush(courseType);

        // Get the courseType
        restCourseTypeMockMvc.perform(get("/api/course-types/{id}", courseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courseType.getId().intValue()))
            .andExpect(jsonPath("$.typeName").value(DEFAULT_TYPE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseType() throws Exception {
        // Get the courseType
        restCourseTypeMockMvc.perform(get("/api/course-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseType() throws Exception {
        // Initialize the database
        courseTypeService.save(courseType);

        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();

        // Update the courseType
        CourseType updatedCourseType = courseTypeRepository.findById(courseType.getId()).get();
        // Disconnect from session so that the updates on updatedCourseType are not directly saved in db
        em.detach(updatedCourseType);
        updatedCourseType
            .typeName(UPDATED_TYPE_NAME);

        restCourseTypeMockMvc.perform(put("/api/course-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourseType)))
            .andExpect(status().isOk());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
        CourseType testCourseType = courseTypeList.get(courseTypeList.size() - 1);
        assertThat(testCourseType.getTypeName()).isEqualTo(UPDATED_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCourseType() throws Exception {
        int databaseSizeBeforeUpdate = courseTypeRepository.findAll().size();

        // Create the CourseType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseTypeMockMvc.perform(put("/api/course-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseType)))
            .andExpect(status().isBadRequest());

        // Validate the CourseType in the database
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourseType() throws Exception {
        // Initialize the database
        courseTypeService.save(courseType);

        int databaseSizeBeforeDelete = courseTypeRepository.findAll().size();

        // Delete the courseType
        restCourseTypeMockMvc.perform(delete("/api/course-types/{id}", courseType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseType> courseTypeList = courseTypeRepository.findAll();
        assertThat(courseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseType.class);
        CourseType courseType1 = new CourseType();
        courseType1.setId(1L);
        CourseType courseType2 = new CourseType();
        courseType2.setId(courseType1.getId());
        assertThat(courseType1).isEqualTo(courseType2);
        courseType2.setId(2L);
        assertThat(courseType1).isNotEqualTo(courseType2);
        courseType1.setId(null);
        assertThat(courseType1).isNotEqualTo(courseType2);
    }
}
