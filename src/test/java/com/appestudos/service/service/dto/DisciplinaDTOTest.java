package com.appestudos.service.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.appestudos.service.web.rest.TestUtil;

public class DisciplinaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisciplinaDTO.class);
        DisciplinaDTO disciplinaDTO1 = new DisciplinaDTO();
        disciplinaDTO1.setId(1L);
        DisciplinaDTO disciplinaDTO2 = new DisciplinaDTO();
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId(disciplinaDTO1.getId());
        assertThat(disciplinaDTO1).isEqualTo(disciplinaDTO2);
        disciplinaDTO2.setId(2L);
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
        disciplinaDTO1.setId(null);
        assertThat(disciplinaDTO1).isNotEqualTo(disciplinaDTO2);
    }
}
