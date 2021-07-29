package com.appestudos.service.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appestudos.service.web.rest.TestUtil;

public class AreaDisciplinaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDisciplina.class);
        AreaDisciplina areaDisciplina1 = new AreaDisciplina();
        areaDisciplina1.setId(1L);
        AreaDisciplina areaDisciplina2 = new AreaDisciplina();
        areaDisciplina2.setId(areaDisciplina1.getId());
        assertThat(areaDisciplina1).isEqualTo(areaDisciplina2);
        areaDisciplina2.setId(2L);
        assertThat(areaDisciplina1).isNotEqualTo(areaDisciplina2);
        areaDisciplina1.setId(null);
        assertThat(areaDisciplina1).isNotEqualTo(areaDisciplina2);
    }
}
