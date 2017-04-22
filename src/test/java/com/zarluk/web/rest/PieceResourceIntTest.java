package com.zarluk.web.rest;

import com.zarluk.SbgManagerApp;

import com.zarluk.domain.Piece;
import com.zarluk.repository.PieceRepository;
import com.zarluk.service.PieceService;
import com.zarluk.service.dto.PieceDTO;
import com.zarluk.service.mapper.PieceMapper;
import com.zarluk.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PieceResource REST controller.
 *
 * @see PieceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbgManagerApp.class)
public class PieceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_REGEX = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    @Autowired
    private PieceRepository pieceRepository;

    @Autowired
    private PieceMapper pieceMapper;

    @Autowired
    private PieceService pieceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPieceMockMvc;

    private Piece piece;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PieceResource pieceResource = new PieceResource(pieceService);
        this.restPieceMockMvc = MockMvcBuilders.standaloneSetup(pieceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Piece createEntity(EntityManager em) {
        Piece piece = new Piece()
            .name(DEFAULT_NAME)
            .regex(DEFAULT_REGEX)
            .value(DEFAULT_VALUE);
        return piece;
    }

    @Before
    public void initTest() {
        piece = createEntity(em);
    }

    @Test
    @Transactional
    public void createPiece() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece
        PieceDTO pieceDTO = pieceMapper.pieceToPieceDTO(piece);
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isCreated());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate + 1);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPiece.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testPiece.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece with an existing ID
        piece.setId(1L);
        PieceDTO pieceDTO = pieceMapper.pieceToPieceDTO(piece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPieces() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get all the pieceList
        restPieceMockMvc.perform(get("/api/pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piece.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].regex").value(hasItem(DEFAULT_REGEX.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getPiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(piece.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.regex").value(DEFAULT_REGEX.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPiece() throws Exception {
        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);
        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Update the piece
        Piece updatedPiece = pieceRepository.findOne(piece.getId());
        updatedPiece
            .name(UPDATED_NAME)
            .regex(UPDATED_REGEX)
            .value(UPDATED_VALUE);
        PieceDTO pieceDTO = pieceMapper.pieceToPieceDTO(updatedPiece);

        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isOk());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPiece.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testPiece.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPiece() throws Exception {
        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Create the Piece
        PieceDTO pieceDTO = pieceMapper.pieceToPieceDTO(piece);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceDTO)))
            .andExpect(status().isCreated());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);
        int databaseSizeBeforeDelete = pieceRepository.findAll().size();

        // Get the piece
        restPieceMockMvc.perform(delete("/api/pieces/{id}", piece.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Piece.class);
    }
}
