package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@ToString

@Table(schema = "public", name = "especialidades" )

public class Especialidade extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "nome", nullable = false, length =50, unique = true)
    private String nome;


}
