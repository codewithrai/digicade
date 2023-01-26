package com.digicade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.digicade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlayerGameBadgeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerGameBadgeDTO.class);
        PlayerGameBadgeDTO playerGameBadgeDTO1 = new PlayerGameBadgeDTO();
        playerGameBadgeDTO1.setId(1L);
        PlayerGameBadgeDTO playerGameBadgeDTO2 = new PlayerGameBadgeDTO();
        assertThat(playerGameBadgeDTO1).isNotEqualTo(playerGameBadgeDTO2);
        playerGameBadgeDTO2.setId(playerGameBadgeDTO1.getId());
        assertThat(playerGameBadgeDTO1).isEqualTo(playerGameBadgeDTO2);
        playerGameBadgeDTO2.setId(2L);
        assertThat(playerGameBadgeDTO1).isNotEqualTo(playerGameBadgeDTO2);
        playerGameBadgeDTO1.setId(null);
        assertThat(playerGameBadgeDTO1).isNotEqualTo(playerGameBadgeDTO2);
    }
}
