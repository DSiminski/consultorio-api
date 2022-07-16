package br.com.consultorio.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString

@Table(schema = "public", name = "pacientes" )

public class Paciente extends Pessoa {

    @Getter
    @Setter
    @JoinColumn(name = "id_convenio")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Convenio convenio;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoAgendamento", nullable = true)
    private TipoAtendimento tipoAtendimento;

    @Getter
    @Setter
    @Column(name = "numeroCartaoConvenio",nullable = true, length = 50,unique = true)
    private String numeroCartaoConvenio;

    @Getter
    @Setter
    @Column(name = "dataVencimento", nullable = true)
    private LocalDateTime dataVencimento;


}
