package br.com.consultorio.repository;

import br.com.consultorio.entity.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Repository
@RequestMapping
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {
    @Modifying
    @Query("UPDATE Convenio convenio " +
    "SET convenio.ativo = false "+
    "WHERE convenio.id = :convenio")
    public void updateStatus(
            @Param("convenio")Long idConvenio);
}
