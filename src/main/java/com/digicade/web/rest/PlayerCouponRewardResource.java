package com.digicade.web.rest;

import com.digicade.repository.PlayerCouponRewardRepository;
import com.digicade.service.PlayerCouponRewardService;
import com.digicade.service.dto.PlayerCouponRewardDTO;
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
 * REST controller for managing {@link com.digicade.domain.PlayerCouponReward}.
 */
@RestController
@RequestMapping("/api")
public class PlayerCouponRewardResource {

    private final Logger log = LoggerFactory.getLogger(PlayerCouponRewardResource.class);

    private static final String ENTITY_NAME = "playerCouponReward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayerCouponRewardService playerCouponRewardService;

    private final PlayerCouponRewardRepository playerCouponRewardRepository;

    public PlayerCouponRewardResource(
        PlayerCouponRewardService playerCouponRewardService,
        PlayerCouponRewardRepository playerCouponRewardRepository
    ) {
        this.playerCouponRewardService = playerCouponRewardService;
        this.playerCouponRewardRepository = playerCouponRewardRepository;
    }

    /**
     * {@code POST  /player-coupon-rewards} : Create a new playerCouponReward.
     *
     * @param playerCouponRewardDTO the playerCouponRewardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new playerCouponRewardDTO, or with status {@code 400 (Bad Request)} if the playerCouponReward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/player-coupon-rewards")
    public ResponseEntity<PlayerCouponRewardDTO> createPlayerCouponReward(@RequestBody PlayerCouponRewardDTO playerCouponRewardDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlayerCouponReward : {}", playerCouponRewardDTO);
        if (playerCouponRewardDTO.getId() != null) {
            throw new BadRequestAlertException("A new playerCouponReward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlayerCouponRewardDTO result = playerCouponRewardService.save(playerCouponRewardDTO);
        return ResponseEntity
            .created(new URI("/api/player-coupon-rewards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /player-coupon-rewards/:id} : Updates an existing playerCouponReward.
     *
     * @param id the id of the playerCouponRewardDTO to save.
     * @param playerCouponRewardDTO the playerCouponRewardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerCouponRewardDTO,
     * or with status {@code 400 (Bad Request)} if the playerCouponRewardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the playerCouponRewardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/player-coupon-rewards/{id}")
    public ResponseEntity<PlayerCouponRewardDTO> updatePlayerCouponReward(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlayerCouponRewardDTO playerCouponRewardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlayerCouponReward : {}, {}", id, playerCouponRewardDTO);
        if (playerCouponRewardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerCouponRewardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerCouponRewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlayerCouponRewardDTO result = playerCouponRewardService.update(playerCouponRewardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, playerCouponRewardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /player-coupon-rewards/:id} : Partial updates given fields of an existing playerCouponReward, field will ignore if it is null
     *
     * @param id the id of the playerCouponRewardDTO to save.
     * @param playerCouponRewardDTO the playerCouponRewardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated playerCouponRewardDTO,
     * or with status {@code 400 (Bad Request)} if the playerCouponRewardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the playerCouponRewardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the playerCouponRewardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/player-coupon-rewards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlayerCouponRewardDTO> partialUpdatePlayerCouponReward(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlayerCouponRewardDTO playerCouponRewardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlayerCouponReward partially : {}, {}", id, playerCouponRewardDTO);
        if (playerCouponRewardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, playerCouponRewardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!playerCouponRewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlayerCouponRewardDTO> result = playerCouponRewardService.partialUpdate(playerCouponRewardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, playerCouponRewardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /player-coupon-rewards} : get all the playerCouponRewards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of playerCouponRewards in body.
     */
    @GetMapping("/player-coupon-rewards")
    public ResponseEntity<List<PlayerCouponRewardDTO>> getAllPlayerCouponRewards(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PlayerCouponRewards");
        Page<PlayerCouponRewardDTO> page = playerCouponRewardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /player-coupon-rewards/:id} : get the "id" playerCouponReward.
     *
     * @param id the id of the playerCouponRewardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the playerCouponRewardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/player-coupon-rewards/{id}")
    public ResponseEntity<PlayerCouponRewardDTO> getPlayerCouponReward(@PathVariable Long id) {
        log.debug("REST request to get PlayerCouponReward : {}", id);
        Optional<PlayerCouponRewardDTO> playerCouponRewardDTO = playerCouponRewardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(playerCouponRewardDTO);
    }

    /**
     * {@code DELETE  /player-coupon-rewards/:id} : delete the "id" playerCouponReward.
     *
     * @param id the id of the playerCouponRewardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/player-coupon-rewards/{id}")
    public ResponseEntity<Void> deletePlayerCouponReward(@PathVariable Long id) {
        log.debug("REST request to delete PlayerCouponReward : {}", id);
        playerCouponRewardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/player-coupon-rewards/coupon/{playerId}")
    public ResponseEntity<PlayerCouponRewardDTO> getPlayerCouponRewardByPlayerId(@PathVariable Long playerId) throws Exception {
        PlayerCouponRewardDTO playerCouponRewardDTO = playerCouponRewardService.getPlayerCouponRewardByPlayerId(playerId);

        return ResponseEntity.ok(playerCouponRewardDTO);
    }
}
