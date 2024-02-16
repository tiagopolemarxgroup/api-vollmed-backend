package com.api.clinica.controller;

import com.api.clinica.domain.consulta.AgendaConsultaService;
import com.api.clinica.domain.consulta.DadosAgendamentoConsulta;
import com.api.clinica.domain.consulta.DadosDetalhamentoDaConsulta;
import com.api.clinica.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AgendaConsultaService agendaConsultaService;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoDaConsulta> dadosDetalhamentoDaConsultaJson;

    @WithMockUser
    @Test
    @DisplayName("Deveria devolver http 400 quando informações estão inválidas")
    void agendar_cenario1() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @WithMockUser
    @Test
    @DisplayName("Deveria devolver http 400 quando informações estão inválidas")
    void agendar_cenario2() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/consultas")).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @WithMockUser
    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informações estão válidas")
    void agendar_cenario3() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoDaConsulta(null, 1L, 1L, data);

        Mockito.when(agendaConsultaService.agendar(any())).thenReturn(dadosDetalhamento);

        MockHttpServletResponse response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJson.write(new DadosAgendamentoConsulta(1L, 1L, data, especialidade))
                        .getJson())).andReturn().getResponse();

        //assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoDaConsultaJson.write(dadosDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}