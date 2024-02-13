package com.api.clinica.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoDaConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime date) {
    public DadosDetalhamentoDaConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
