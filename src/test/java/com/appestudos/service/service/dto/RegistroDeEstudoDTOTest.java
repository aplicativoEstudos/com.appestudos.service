package com.appestudos.service.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appestudos.service.web.rest.TestUtil;

public class RegistroDeEstudoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroDeEstudoDTO.class);
        RegistroDeEstudoDTO registroDeEstudoDTO1 = new RegistroDeEstudoDTO();
        registroDeEstudoDTO1.setId(1L);
        RegistroDeEstudoDTO registroDeEstudoDTO2 = new RegistroDeEstudoDTO();
        assertThat(registroDeEstudoDTO1).isNotEqualTo(registroDeEstudoDTO2);
        registroDeEstudoDTO2.setId(registroDeEstudoDTO1.getId());
        assertThat(registroDeEstudoDTO1).isEqualTo(registroDeEstudoDTO2);
        registroDeEstudoDTO2.setId(2L);
        assertThat(registroDeEstudoDTO1).isNotEqualTo(registroDeEstudoDTO2);
        registroDeEstudoDTO1.setId(null);
        assertThat(registroDeEstudoDTO1).isNotEqualTo(registroDeEstudoDTO2);
    }
}
