package br.com.consultorio.service;

import br.com.consultorio.entity.*;
import br.com.consultorio.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class HistoricoService {
    @Autowired
    public HistoricoRepository historicoRepository;

    public void createHistorico(Agendamento agendamento, StatusAgendamento statusAgendamento,
                                LocalDateTime data, Paciente paciente, Secretaria secretaria, Medico medico) {
        Historico historico = new Historico(agendamento, paciente, medico, secretaria, statusAgendamento, data, "");
        historico.setAtivo(true);
        this.insert(historico);
    }

    public void insert(Historico historico) {
        this.saveTransaction(historico);
    }

    @Transactional
    public void saveTransaction(Historico historico) {
        this.historicoRepository.save(historico);
    }

    public Optional<Historico> findById(Long id) {
        return this.historicoRepository.findById(id);
    }

    public Page<Historico> listAll(Pageable pageable) {
        return this.historicoRepository.findAll(pageable);
    }

}
