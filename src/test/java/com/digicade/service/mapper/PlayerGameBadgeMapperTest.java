package com.digicade.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerGameBadgeMapperTest {

    private PlayerGameBadgeMapper playerGameBadgeMapper;

    @BeforeEach
    public void setUp() {
        playerGameBadgeMapper = new PlayerGameBadgeMapperImpl();
    }
}
