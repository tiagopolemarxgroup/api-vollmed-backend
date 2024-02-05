package com.api.clinica.domain.medico;

import com.api.clinica.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(@NotNull Long id, String nome, String telefone, DadosEndereco endereco) {
}
