package com.appestudos.service.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistroDeEstudoMapperTest {

    private RegistroDeEstudoMapper registroDeEstudoMapper;

    @BeforeEach
    public void setUp() {
        registroDeEstudoMapper = new RegistroDeEstudoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(registroDeEstudoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(registroDeEstudoMapper.fromId(null)).isNull();
    }
}
