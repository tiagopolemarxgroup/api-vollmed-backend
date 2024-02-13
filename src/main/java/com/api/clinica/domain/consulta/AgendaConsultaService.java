package com.api.clinica.domain.consulta;

import com.api.clinica.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import com.api.clinica.domain.medico.Medico;
import com.api.clinica.domain.medico.MedicoRepository;
import com.api.clinica.domain.paciente.Paciente;
import com.api.clinica.domain.paciente.PacienteRepository;
import com.api.clinica.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AgendaConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validacoes;

    public DadosDetalhamentoDaConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        if (Objects.nonNull(dados.idMedico()) && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }

        validacoes.forEach(v -> v.validar(dados));

        Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        Medico medico = escolherMedicos(dados);
        if(medico == null){
            throw new ValidacaoException("N~]ao existe medico disponivel nessa data");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoDaConsulta(consulta);
    }

    private Medico escolherMedicos(DadosAgendamentoConsulta dados) {
        if (Objects.nonNull(dados.idMedico())) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (Objects.isNull(dados.especialidadeMedico())) {
            throw new ValidacaoException("Especialidade  é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidadeMedico(), dados.data());
    }
}
