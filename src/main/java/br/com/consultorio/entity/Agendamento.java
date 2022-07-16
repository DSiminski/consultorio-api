package br.com.consultorio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor

@Table(schema = "public", name = "agendamentos" )
public class Agendamento extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name = "id_paciente")
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @Getter @Setter
    @JoinColumn(name = "id_secretaria")
    @ManyToOne(fetch = FetchType.EAGER)
    private Secretaria secretaria;

    @Getter @Setter
    @JoinColumn(name = "id_medico")
    @ManyToOne(fetch = FetchType.EAGER)
    private Medico medico;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "statusAgendamento", nullable = false)
    private StatusAgendamento statusAgendamento;

    @Getter
    @Setter
    @Column(name = "dataDe", nullable = false)
    private LocalDateTime dataDe;

    @Getter
    @Setter
    @Column(name = "dataAte", nullable = false)
    private LocalDateTime dataAte;

    @Getter
    @Setter
    @Column(name = "encaixe", nullable = false)
    private Boolean encaixe;


}
