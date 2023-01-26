package com.digicade.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.digicade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlayerGameBadgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlayerGameBadge.class);
        PlayerGameBadge playerGameBadge1 = new PlayerGameBadge();
        playerGameBadge1.setId(1L);
        PlayerGameBadge playerGameBadge2 = new PlayerGameBadge();
        playerGameBadge2.setId(playerGameBadge1.getId());
        assertThat(playerGameBadge1).isEqualTo(playerGameBadge2);
        playerGameBadge2.setId(2L);
        assertThat(playerGameBadge1).isNotEqualTo(playerGameBadge2);
        playerGameBadge1.setId(null);
        assertThat(playerGameBadge1).isNotEqualTo(playerGameBadge2);
    }
}
