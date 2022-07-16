package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
@Entity
@NoArgsConstructor
@ToString
@Table(schema = "public", name = "medicos" )

public class Medico extends Pessoa{

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "especialidade", nullable = false)
    private Especialidade especialidade;

    @Getter
    @Setter
    @Column(name = "CRM", nullable = false, length = 50, unique = true)
    private String CRM;

    @Getter
    @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "porcenParticipacao")
    private BigDecimal porcenParticipacao;

    @Getter
    @Setter
    @Column(name = "consultorio", nullable = true, length = 50)
    private String consultorio;

    @Getter
    @Setter
    @Digits(integer = 5, fraction = 3)
    @Column(name = "valor")
    private BigDecimal valor;
}
