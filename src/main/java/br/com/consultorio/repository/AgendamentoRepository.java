package br.com.consultorio.repository;

import br.com.consultorio.entity.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "SET agendamento.ativo = false " +
            "WHERE agendamento.id = :agendamento")
    public void updateStatus(
            @Param("agendamento") Long idAgenda);

    @Query("FROM Agendamento agendamento " +
            "WHERE (:datade BETWEEN agendamento.dataDe AND agendamento.dataAte " +
            "OR :dataAte BETWEEN agendamento.dataDe AND agendamento.dataAte) " +
            "AND (agendamento.medico.id = :medico OR agendamento.paciente.id = :paciente) " +
            "AND agendamento.id <> :agendamento")
    public List<Agendamento> conflitoMedicoPaciente(
            @Param("agendamento") Long idAgendamento,
            @Param("datade") LocalDateTime dataDe,
            @Param("dataAte") LocalDateTime dataAte,
            @Param("medico") Long idMedico,
            @Param("paciente") Long idPaciente
    );
    @Query("FROM Agendamento agendamento " +
            "WHERE extract(day from agendamento.dataDe) = extract(day from now()) " +
            "AND extract(day from agendamento.dataAte) = extract(day from now())")
    public Page<Agendamento> findAllToday(
            Pageable pageable
    );
}
