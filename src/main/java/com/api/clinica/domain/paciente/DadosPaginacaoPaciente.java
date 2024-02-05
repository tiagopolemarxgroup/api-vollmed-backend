package com.api.clinica.domain.paciente;

public record DadosPaginacaoPaciente(String nome, String email, String telefone, String cpf) {
    public DadosPaginacaoPaciente(Paciente paciente){
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf());
    }
}
