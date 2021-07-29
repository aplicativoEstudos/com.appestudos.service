package com.appestudos.service.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appestudos.service.web.rest.TestUtil;

public class AreaDisciplinaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaDisciplinaDTO.class);
        AreaDisciplinaDTO areaDisciplinaDTO1 = new AreaDisciplinaDTO();
        areaDisciplinaDTO1.setId(1L);
        AreaDisciplinaDTO areaDisciplinaDTO2 = new AreaDisciplinaDTO();
        assertThat(areaDisciplinaDTO1).isNotEqualTo(areaDisciplinaDTO2);
        areaDisciplinaDTO2.setId(areaDisciplinaDTO1.getId());
        assertThat(areaDisciplinaDTO1).isEqualTo(areaDisciplinaDTO2);
        areaDisciplinaDTO2.setId(2L);
        assertThat(areaDisciplinaDTO1).isNotEqualTo(areaDisciplinaDTO2);
        areaDisciplinaDTO1.setId(null);
        assertThat(areaDisciplinaDTO1).isNotEqualTo(areaDisciplinaDTO2);
    }
}
