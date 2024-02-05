package com.api.clinica.domain.paciente;

public record ListagemPacienteDto(Long id, String nome, String email, String telefone, String cpf) {

    public ListagemPacienteDto(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
