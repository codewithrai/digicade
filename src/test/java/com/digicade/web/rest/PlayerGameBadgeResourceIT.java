package com.digicade.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.digicade.IntegrationTest;
import com.digicade.domain.PlayerGameBadge;
import com.digicade.repository.PlayerGameBadgeRepository;
import com.digicade.service.dto.PlayerGameBadgeDTO;
import com.digicade.service.mapper.PlayerGameBadgeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlayerGameBadgeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerGameBadgeResourceIT {

    private static final String ENTITY_API_URL = "/api/player-game-badges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlayerGameBadgeRepository playerGameBadgeRepository;

    @Autowired
    private PlayerGameBadgeMapper playerGameBadgeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerGameBadgeMockMvc;

    private PlayerGameBadge playerGameBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerGameBadge createEntity(EntityManager em) {
        PlayerGameBadge playerGameBadge = new PlayerGameBadge();
        return playerGameBadge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerGameBadge createUpdatedEntity(EntityManager em) {
        PlayerGameBadge playerGameBadge = new PlayerGameBadge();
        return playerGameBadge;
    }

    @BeforeEach
    public void initTest() {
        playerGameBadge = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayerGameBadge() throws Exception {
        int databaseSizeBeforeCreate = playerGameBadgeRepository.findAll().size();
        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);
        restPlayerGameBadgeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerGameBadge testPlayerGameBadge = playerGameBadgeList.get(playerGameBadgeList.size() - 1);
    }

    @Test
    @Transactional
    void createPlayerGameBadgeWithExistingId() throws Exception {
        // Create the PlayerGameBadge with an existing ID
        playerGameBadge.setId(1L);
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        int databaseSizeBeforeCreate = playerGameBadgeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerGameBadgeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlayerGameBadges() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        // Get all the playerGameBadgeList
        restPlayerGameBadgeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerGameBadge.getId().intValue())));
    }

    @Test
    @Transactional
    void getPlayerGameBadge() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        // Get the playerGameBadge
        restPlayerGameBadgeMockMvc
            .perform(get(ENTITY_API_URL_ID, playerGameBadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(playerGameBadge.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlayerGameBadge() throws Exception {
        // Get the playerGameBadge
        restPlayerGameBadgeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlayerGameBadge() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();

        // Update the playerGameBadge
        PlayerGameBadge updatedPlayerGameBadge = playerGameBadgeRepository.findById(playerGameBadge.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerGameBadge are not directly saved in db
        em.detach(updatedPlayerGameBadge);
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(updatedPlayerGameBadge);

        restPlayerGameBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerGameBadgeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
        PlayerGameBadge testPlayerGameBadge = playerGameBadgeList.get(playerGameBadgeList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerGameBadgeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerGameBadgeWithPatch() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();

        // Update the playerGameBadge using partial update
        PlayerGameBadge partialUpdatedPlayerGameBadge = new PlayerGameBadge();
        partialUpdatedPlayerGameBadge.setId(playerGameBadge.getId());

        restPlayerGameBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerGameBadge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayerGameBadge))
            )
            .andExpect(status().isOk());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
        PlayerGameBadge testPlayerGameBadge = playerGameBadgeList.get(playerGameBadgeList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdatePlayerGameBadgeWithPatch() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();

        // Update the playerGameBadge using partial update
        PlayerGameBadge partialUpdatedPlayerGameBadge = new PlayerGameBadge();
        partialUpdatedPlayerGameBadge.setId(playerGameBadge.getId());

        restPlayerGameBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayerGameBadge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayerGameBadge))
            )
            .andExpect(status().isOk());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
        PlayerGameBadge testPlayerGameBadge = playerGameBadgeList.get(playerGameBadgeList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerGameBadgeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayerGameBadge() throws Exception {
        int databaseSizeBeforeUpdate = playerGameBadgeRepository.findAll().size();
        playerGameBadge.setId(count.incrementAndGet());

        // Create the PlayerGameBadge
        PlayerGameBadgeDTO playerGameBadgeDTO = playerGameBadgeMapper.toDto(playerGameBadge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerGameBadgeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerGameBadgeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlayerGameBadge in the database
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayerGameBadge() throws Exception {
        // Initialize the database
        playerGameBadgeRepository.saveAndFlush(playerGameBadge);

        int databaseSizeBeforeDelete = playerGameBadgeRepository.findAll().size();

        // Delete the playerGameBadge
        restPlayerGameBadgeMockMvc
            .perform(delete(ENTITY_API_URL_ID, playerGameBadge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerGameBadge> playerGameBadgeList = playerGameBadgeRepository.findAll();
        assertThat(playerGameBadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
