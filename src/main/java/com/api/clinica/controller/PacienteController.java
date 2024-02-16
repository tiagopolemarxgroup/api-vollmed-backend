package com.api.clinica.controller;

import com.api.clinica.domain.paciente.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder componentsBuilder) {
        Paciente paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = componentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId());
        return ResponseEntity.created(uri.toUri()).body(new DetalhamentoPaciente(paciente));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ListagemPacienteDto>> listarTodos() {
        var listagem = pacienteRepository.findAll().stream().map(ListagemPacienteDto::new).toList();
        return ResponseEntity.ok(listagem);
    }

    @GetMapping
    public ResponseEntity<Page<DadosPaginacaoPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<DadosPaginacaoPaciente> page = pacienteRepository.findAll(paginacao).map(DadosPaginacaoPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoPaciente> detalhar(@PathVariable Long id) {
        Paciente referenceById = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DetalhamentoPaciente(referenceById));
    }

    @Transactional
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid AtualizarDadosPacienteDto dto) {
        Paciente paciente = pacienteRepository.getReferenceById(dto.id());
        paciente.AtualizarPaciente(dto);
        pacienteRepository.save(paciente);
        return ResponseEntity.ok(new DetalhamentoPaciente(paciente));

    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();

    }
}
