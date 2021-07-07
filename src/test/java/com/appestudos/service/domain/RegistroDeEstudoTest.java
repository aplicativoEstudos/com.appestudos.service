package com.appestudos.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appestudos.service.web.rest.TestUtil;

public class RegistroDeEstudoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroDeEstudo.class);
        RegistroDeEstudo registroDeEstudo1 = new RegistroDeEstudo();
        registroDeEstudo1.setId(1L);
        RegistroDeEstudo registroDeEstudo2 = new RegistroDeEstudo();
        registroDeEstudo2.setId(registroDeEstudo1.getId());
        assertThat(registroDeEstudo1).isEqualTo(registroDeEstudo2);
        registroDeEstudo2.setId(2L);
        assertThat(registroDeEstudo1).isNotEqualTo(registroDeEstudo2);
        registroDeEstudo1.setId(null);
        assertThat(registroDeEstudo1).isNotEqualTo(registroDeEstudo2);
    }
}
