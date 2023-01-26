package com.digicade.service;

import com.digicade.service.dto.PlayerGameBadgeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.digicade.domain.PlayerGameBadge}.
 */
public interface PlayerGameBadgeService {
    /**
     * Save a playerGameBadge.
     *
     * @param playerGameBadgeDTO the entity to save.
     * @return the persisted entity.
     */
    PlayerGameBadgeDTO save(PlayerGameBadgeDTO playerGameBadgeDTO);

    /**
     * Updates a playerGameBadge.
     *
     * @param playerGameBadgeDTO the entity to update.
     * @return the persisted entity.
     */
    PlayerGameBadgeDTO update(PlayerGameBadgeDTO playerGameBadgeDTO);

    /**
     * Partially updates a playerGameBadge.
     *
     * @param playerGameBadgeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlayerGameBadgeDTO> partialUpdate(PlayerGameBadgeDTO playerGameBadgeDTO);

    /**
     * Get all the playerGameBadges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlayerGameBadgeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" playerGameBadge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlayerGameBadgeDTO> findOne(Long id);

    /**
     * Delete the "id" playerGameBadge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
