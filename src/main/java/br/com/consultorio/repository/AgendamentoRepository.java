package br.com.consultorio.repository;

import br.com.consultorio.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequestMapping
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    @Modifying
    @Query("UPDATE Agendamento agendamento " +
            "SET agendamento.excluido =:dataExcluido "+
            "WHERE agendamento.id = :agendamento ")
    public void updateStatus(
            @Param("dataExcluido") LocalDateTime dataExcluido,
            @Param("agendamento") Long idAgendamento);
    @Query("SELECT Agendamento FROM Agendamento " +
            "WHERE (:datade BETWEEN Agendamento.dataDe AND Agendamento.dataAte " +
            "OR :dataAte BETWEEN Agendamento.dataDe AND Agendamento.dataAte) " +
            "AND (Agendamento.medico = :medico " +
            "OR Agendamento.paciente = :paciente)")
    public List<Agendamento> conflitoMedicoPaciente(
            @Param("datade") LocalDateTime dataDe,
            @Param("dataate") LocalDateTime dataAte,
            @Param("medico") Long idMedico,
            @Param("paciente") Long idPaciente
    );
}
