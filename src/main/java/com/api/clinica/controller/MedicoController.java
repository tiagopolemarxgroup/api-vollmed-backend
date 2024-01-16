package com.api.clinica.controller;

import com.api.clinica.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    MedicoRepository medicoRepository;

    @Transactional
    @PostMapping
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        Medico save = medicoRepository.save(new Medico(dados));
        System.out.println(save);
    }

    @GetMapping("/todos")
    public List<DadosListagemMedico> listarTodos() {
        return medicoRepository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
    @GetMapping
    public Page<DadosListagemMedico> listarByAtivo(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

    @Transactional
    @PutMapping
    public void  atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        medicoRepository.save(medico);
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    public void excluir(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }
}
