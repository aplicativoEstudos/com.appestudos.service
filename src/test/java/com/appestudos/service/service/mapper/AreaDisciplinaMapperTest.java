package com.appestudos.service.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AreaDisciplinaMapperTest {

    private AreaDisciplinaMapper areaDisciplinaMapper;

    @BeforeEach
    public void setUp() {
        areaDisciplinaMapper = new AreaDisciplinaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(areaDisciplinaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(areaDisciplinaMapper.fromId(null)).isNull();
    }
}
