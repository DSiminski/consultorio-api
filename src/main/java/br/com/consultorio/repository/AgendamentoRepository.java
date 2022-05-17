package br.com.consultorio.repository;

import br.com.consultorio.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

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
}
