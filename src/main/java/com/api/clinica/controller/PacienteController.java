package com.api.clinica.controller;

import com.api.clinica.paciente.DadosCadastroPaciente;
import com.api.clinica.paciente.DadosPaginacaoPaciente;
import com.api.clinica.paciente.Paciente;
import com.api.clinica.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    @PostMapping
    public void salvar(@RequestBody @Valid DadosCadastroPaciente dados){
        pacienteRepository.save(new Paciente(dados));
        System.out.println(dados);
    }

    @GetMapping
    public Page<DadosPaginacaoPaciente> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return pacienteRepository.findAll(paginacao).map(DadosPaginacaoPaciente::new);
    }
}
