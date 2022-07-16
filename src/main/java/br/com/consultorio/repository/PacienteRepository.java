package br.com.consultorio.repository;

import br.com.consultorio.entity.Paciente;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Repository
@RequestMapping
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Modifying
    @Query("UPDATE Paciente paciente " +
    "SET paciente.ativo = false " +
    "WHERE paciente.id = :paciente")
    public void updateStatus(
            @Param("paciente") Long idPaciente);

}
