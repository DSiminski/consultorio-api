package br.com.consultorio.entity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@ToString

public abstract class Pessoa extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "name",nullable = false)
    private String nome;

    @Getter
    @Setter
    @Column(name = "cpf",nullable = false, unique = true)
    private String cpf;

    @Getter
    @Setter
    @Column(name = "rg",nullable = false, unique = true)
    private String rg;

    @Getter
    @Setter
    @Column(name = "cep",nullable = false)
    private String cep;

    @Getter
    @Setter
    @Column(name = "telefone",nullable = false)
    private String telefone;

    @Getter
    @Setter
    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "login",nullable = false)
    private String login;

    @Getter
    @Setter
    @Column(name = "senha",nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo",nullable = false)
    private Sexo sexo;

    @Getter
    @Setter
    @Column(name = "nacionalidade",nullable = false)
    private String nacionalidade;


}
