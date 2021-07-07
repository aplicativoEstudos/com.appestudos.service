package com.appestudos.service.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DisciplinaMapperTest {

    private DisciplinaMapper disciplinaMapper;

    @BeforeEach
    public void setUp() {
        disciplinaMapper = new DisciplinaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(disciplinaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(disciplinaMapper.fromId(null)).isNull();
    }
}
