package br.com.consultorio.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(schema = "public", name = "historicos" )

public class Historico extends AbstractEntity{

    @Getter
    @Setter
    @JoinColumn(name = "id_agenda")
    @ManyToOne
    private Agendamento agenda;

    @Getter
    @Setter
    @JoinColumn(name = "id_paciente")
    @ManyToOne
    private Paciente paciente;

    @Getter
    @Setter
    @JoinColumn(name = "id_secretaria")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "statusAgendamento", nullable = false)
    private StatusAgendamento statusAgendamento;

    @Getter
    @Setter
    @Column(name = "data",nullable = false)
    private LocalDateTime data;

    @Getter
    @Setter
    @Column(name = "observacao", nullable = true, length =100, unique = true)
    private String observacao;


}
