package com.digicade.web.rest;

import com.digicade.repository.PlayerGameBadgeRepository;
import com.digicade.service.PlayerGameBadgeService;
import com.digicade.service.dto.PlayerGameBadgeDTO;
import com.digicade.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.digicade.domain.PlayerGameBadge}.
 */
@RestController
@RequestMapping("/api")
public class PlayerGameBadgeResource {

    private final Logger log = LoggerFactory.getLogger(PlayerGameBadgeResource.class);

    private static final String ENTITY_NAME = "playerGameBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerGameBadgeService playerGameBadgeService;

    private final PlayerGameBadgeRepository playerGameBadgeRepository;

    public PlayerGameBadgeResource(PlayerGameBadgeService playerGameBadgeService, PlayerGameBadgeRepository playerGameBadgeRepository) {
        this.playerGameBadgeService = playerGameBadgeService;
        this.playerGameBadgeRepository = playerGameBadgeRepository;
    }

    /**
     * {@code POST  /player-game-badges} : Create a new playerGameBadge.
     *
     * @param playerGameBadgeDTO the playerGameBadgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerGameBadgeDTO, or with status {@code 400 (Bad Request)} if the playerGameBadge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-game-badges")
    public ResponseEntity<PlayerGameBadgeDTO> createPlayerGameBadge(@RequestBody PlayerGameBadgeDTO playerGameBadgeDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlayerGameBadge : {}", playerGameBadgeDTO);
        if (playerGameBadgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerGameBadge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerGameBadgeDTO result = playerGameBadgeService.save(playerGameBadgeDTO);
        return ResponseEntity
            .created(new URI("/api/player-game-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-game-badges/:id} : Updates an existing playerGameBadge.
     *
     * @param id the id of the playerGameBadgeDTO to save.
     * @param playerGameBadgeDTO the playerGameBadgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerGameBadgeDTO,
     * or with status {@code 400 (Bad Request)} if the playerGameBadgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerGameBadgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-game-badges/{id}")
    public ResponseEntity<PlayerGameBadgeDTO> updatePlayerGameBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlayerGameBadgeDTO playerGameBadgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlayerGameBadge : {}, {}", id, playerGameBadgeDTO);
        if (playerGameBadgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerGameBadgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerGameBadgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlayerGameBadgeDTO result = playerGameBadgeService.update(playerGameBadgeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, playerGameBadgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /player-game-badges/:id} : Partial updates given fields of an existing playerGameBadge, field will ignore if it is null
     *
     * @param id the id of the playerGameBadgeDTO to save.
     * @param playerGameBadgeDTO the playerGameBadgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerGameBadgeDTO,
     * or with status {@code 400 (Bad Request)} if the playerGameBadgeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the playerGameBadgeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the playerGameBadgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/player-game-badges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlayerGameBadgeDTO> partialUpdatePlayerGameBadge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlayerGameBadgeDTO playerGameBadgeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlayerGameBadge partially : {}, {}", id, playerGameBadgeDTO);
        if (playerGameBadgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerGameBadgeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerGameBadgeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlayerGameBadgeDTO> result = playerGameBadgeService.partialUpdate(playerGameBadgeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, playerGameBadgeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /player-game-badges} : get all the playerGameBadges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerGameBadges in body.
     */
    @GetMapping("/player-game-badges")
    public ResponseEntity<List<PlayerGameBadgeDTO>> getAllPlayerGameBadges(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlayerGameBadges");
        Page<PlayerGameBadgeDTO> page = playerGameBadgeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-game-badges/:id} : get the "id" playerGameBadge.
     *
     * @param id the id of the playerGameBadgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerGameBadgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-game-badges/{id}")
    public ResponseEntity<PlayerGameBadgeDTO> getPlayerGameBadge(@PathVariable Long id) {
        log.debug("REST request to get PlayerGameBadge : {}", id);
        Optional<PlayerGameBadgeDTO> playerGameBadgeDTO = playerGameBadgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerGameBadgeDTO);
    }

    /**
     * {@code DELETE  /player-game-badges/:id} : delete the "id" playerGameBadge.
     *
     * @param id the id of the playerGameBadgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-game-badges/{id}")
    public ResponseEntity<Void> deletePlayerGameBadge(@PathVariable Long id) {
        log.debug("REST request to delete PlayerGameBadge : {}", id);
        playerGameBadgeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
