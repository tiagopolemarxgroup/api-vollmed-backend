package com.api.clinica.controller;

import com.api.clinica.domain.consulta.AgendaConsultaService;
import com.api.clinica.domain.consulta.DadosAgendamentoConsulta;
import com.api.clinica.domain.consulta.DadosDetalhamentoDaConsulta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private AgendaConsultaService agendaConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agendaConsultaService.agendar(dados);
        return ResponseEntity.ok(dto);
    }


}
