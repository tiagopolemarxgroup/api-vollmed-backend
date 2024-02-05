package com.api.clinica.domain.medico;

import com.api.clinica.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
        @NotBlank
        String nome,
        @Email
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotBlank
        String telefone,
        @NotNull
        Especialidade especialidade,
        @NotNull @Valid
        DadosEndereco endereco) {
}
