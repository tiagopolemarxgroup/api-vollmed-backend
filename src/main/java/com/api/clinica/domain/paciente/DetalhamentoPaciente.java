package com.api.clinica.domain.paciente;

import com.api.clinica.domain.endereco.Endereco;

public record DetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf, Endereco endereco) {

    public DetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(),paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }
}
