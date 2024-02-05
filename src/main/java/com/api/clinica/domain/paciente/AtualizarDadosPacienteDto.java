package com.api.clinica.domain.paciente;

import com.api.clinica.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record AtualizarDadosPacienteDto(@NotNull Long id, String nome, String email, String telefone, DadosEndereco endereco) {

}
