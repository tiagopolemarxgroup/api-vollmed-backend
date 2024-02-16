package com.api.clinica.controller;

import com.api.clinica.domain.medico.*;
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
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        var uri = uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId());
        return ResponseEntity.created(uri.toUri()).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<DadosListagemMedico>> listarTodos() {
        var list = medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listarByAtivo(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detathar(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

    @Transactional
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        medicoRepository.save(medico);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }
}
