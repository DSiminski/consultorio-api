package br.com.consultorio.repository;

import br.com.consultorio.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Repository
@RequestMapping
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Modifying
    //@Query solicita informações do banco de dados e retorna uma tabela ou conjunto delas
    @Query("UPDATE Medico medico " +
    "SET medico.ativo = false " +
    "WHERE medico.id = :medico")
    public void updateStatus(
            @Param("medico")Long idMedico);


}
