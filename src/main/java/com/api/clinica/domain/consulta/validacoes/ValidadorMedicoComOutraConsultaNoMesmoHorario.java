package com.api.clinica.domain.consulta.validacoes;

import com.api.clinica.domain.consulta.ConsultaRepository;
import com.api.clinica.domain.consulta.DadosAgendamentoConsulta;
import com.api.clinica.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {

        var medicoPossuiOutraConsultaMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if (medicoPossuiOutraConsultaMesmoHorario) {
            throw new ValidacaoException("Médico já possui consulta agendada nesse mesmo horário");

        }
    }
}
