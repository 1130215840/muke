package com.cz.day4_muke.web.rest;

import com.cz.day4_muke.Day4MukeApp;
import com.cz.day4_muke.domain.TeachInfo;
import com.cz.day4_muke.repository.TeachInfoRepository;
import com.cz.day4_muke.service.TeachInfoService;
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
 * Integration tests for the {@Link TeachInfoResource} REST controller.
 */
@SpringBootTest(classes = Day4MukeApp.class)
public class TeachInfoResourceIT {

    @Autowired
    private TeachInfoRepository teachInfoRepository;

    @Autowired
    private TeachInfoService teachInfoService;

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

    private MockMvc restTeachInfoMockMvc;

    private TeachInfo teachInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeachInfoResource teachInfoResource = new TeachInfoResource(teachInfoService);
        this.restTeachInfoMockMvc = MockMvcBuilders.standaloneSetup(teachInfoResource)
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
    public static TeachInfo createEntity(EntityManager em) {
        TeachInfo teachInfo = new TeachInfo();
        return teachInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachInfo createUpdatedEntity(EntityManager em) {
        TeachInfo teachInfo = new TeachInfo();
        return teachInfo;
    }

    @BeforeEach
    public void initTest() {
        teachInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachInfo() throws Exception {
        int databaseSizeBeforeCreate = teachInfoRepository.findAll().size();

        // Create the TeachInfo
        restTeachInfoMockMvc.perform(post("/api/teach-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachInfo)))
            .andExpect(status().isCreated());

        // Validate the TeachInfo in the database
        List<TeachInfo> teachInfoList = teachInfoRepository.findAll();
        assertThat(teachInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TeachInfo testTeachInfo = teachInfoList.get(teachInfoList.size() - 1);
    }

    @Test
    @Transactional
    public void createTeachInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachInfoRepository.findAll().size();

        // Create the TeachInfo with an existing ID
        teachInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachInfoMockMvc.perform(post("/api/teach-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachInfo)))
            .andExpect(status().isBadRequest());

        // Validate the TeachInfo in the database
        List<TeachInfo> teachInfoList = teachInfoRepository.findAll();
        assertThat(teachInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachInfos() throws Exception {
        // Initialize the database
        teachInfoRepository.saveAndFlush(teachInfo);

        // Get all the teachInfoList
        restTeachInfoMockMvc.perform(get("/api/teach-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachInfo.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTeachInfo() throws Exception {
        // Initialize the database
        teachInfoRepository.saveAndFlush(teachInfo);

        // Get the teachInfo
        restTeachInfoMockMvc.perform(get("/api/teach-infos/{id}", teachInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teachInfo.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeachInfo() throws Exception {
        // Get the teachInfo
        restTeachInfoMockMvc.perform(get("/api/teach-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachInfo() throws Exception {
        // Initialize the database
        teachInfoService.save(teachInfo);

        int databaseSizeBeforeUpdate = teachInfoRepository.findAll().size();

        // Update the teachInfo
        TeachInfo updatedTeachInfo = teachInfoRepository.findById(teachInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTeachInfo are not directly saved in db
        em.detach(updatedTeachInfo);

        restTeachInfoMockMvc.perform(put("/api/teach-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeachInfo)))
            .andExpect(status().isOk());

        // Validate the TeachInfo in the database
        List<TeachInfo> teachInfoList = teachInfoRepository.findAll();
        assertThat(teachInfoList).hasSize(databaseSizeBeforeUpdate);
        TeachInfo testTeachInfo = teachInfoList.get(teachInfoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachInfo() throws Exception {
        int databaseSizeBeforeUpdate = teachInfoRepository.findAll().size();

        // Create the TeachInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachInfoMockMvc.perform(put("/api/teach-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachInfo)))
            .andExpect(status().isBadRequest());

        // Validate the TeachInfo in the database
        List<TeachInfo> teachInfoList = teachInfoRepository.findAll();
        assertThat(teachInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeachInfo() throws Exception {
        // Initialize the database
        teachInfoService.save(teachInfo);

        int databaseSizeBeforeDelete = teachInfoRepository.findAll().size();

        // Delete the teachInfo
        restTeachInfoMockMvc.perform(delete("/api/teach-infos/{id}", teachInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeachInfo> teachInfoList = teachInfoRepository.findAll();
        assertThat(teachInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachInfo.class);
        TeachInfo teachInfo1 = new TeachInfo();
        teachInfo1.setId(1L);
        TeachInfo teachInfo2 = new TeachInfo();
        teachInfo2.setId(teachInfo1.getId());
        assertThat(teachInfo1).isEqualTo(teachInfo2);
        teachInfo2.setId(2L);
        assertThat(teachInfo1).isNotEqualTo(teachInfo2);
        teachInfo1.setId(null);
        assertThat(teachInfo1).isNotEqualTo(teachInfo2);
    }
}
