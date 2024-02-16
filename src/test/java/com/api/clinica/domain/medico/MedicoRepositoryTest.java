package com.api.clinica.domain.medico;

import com.api.clinica.domain.consulta.Consulta;
import com.api.clinica.domain.endereco.DadosEndereco;
import com.api.clinica.domain.paciente.DadosCadastroPaciente;
import com.api.clinica.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Deveria devolver null quando unico medico cadastrado não está disponivel na data")
    @Test
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        //given ou arrange
        LocalDateTime proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = cadastrarMedico("Ximba", "ximba@email.com", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastraPaciente("Tiririca", "tiririca@email.com", "12345678910");
        var consulta = cadastrarConsulta(medico, paciente, proximaSegundaAs10);
        //when ou act
        Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        //then ou assert
        assertThat(medicoLivre).isNull();
    }

    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    @Test
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //given ou arrange
        LocalDateTime proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = cadastrarMedico("Ximba", "ximba@email.com", "123456", Especialidade.CARDIOLOGIA);
        //when ou act
        Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        return em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(nome, crm, email, especialidade);
        em.persist(medico);
        return medico;
    }

    private Paciente cadastraPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(nome, email, cpf);
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(nome, email, crm, "41254566321", especialidade, dadosEndereco());
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(nome, email, "11952526598", cpf, dadosEndereco());
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua ximba",
                "cocaia",
                "00000111",
                "Rio De Janeiro",
                "RG",
                null,
                null);
    }
}