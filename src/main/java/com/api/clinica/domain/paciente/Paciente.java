package com.api.clinica.domain.paciente;

import com.api.clinica.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "pacientes")
@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    public Paciente(DadosCadastroPaciente dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    public Paciente(String nome, String email, String cpf) {
        this.ativo = true;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public void AtualizarPaciente(AtualizarDadosPacienteDto dto){
        if(dto.nome() != null){
            this.nome = dto.nome();
        }
        if(dto.email() != null){
            this.email = dto.email();
        }
        if(dto.telefone() != null){
            this.telefone = dto.telefone();
        }
        if(dto.endereco() != null){
            this.endereco.atualizaInformacoes(dto.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
