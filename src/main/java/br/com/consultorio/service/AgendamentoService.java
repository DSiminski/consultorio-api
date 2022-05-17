package br.com.consultorio.service;

import br.com.consultorio.entity.*;
import br.com.consultorio.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    public AgendamentoRepository agendamentoRepository;

    public Page<Agendamento> listAll(Pageable pageable){
        return this.agendamentoRepository.findAll(pageable);
    }

    public Optional<Agendamento> findById(Long id){
        return this.agendamentoRepository.findById(id);
    }

    public void insert(Agendamento agendamento, Secretaria secretaria, String observacao) {
        saveTransaction(agendamento);
        this.validarFormulario(agendamento, secretaria);

    }

    public void saveTransaction(Agendamento agendamento){
        this.agendamentoRepository.save(agendamento);
    }

    public void update(Long id,Agendamento agendamento,Secretaria secretaria,String observacao) {
        if (id == agendamento.getId()) {
            this.validarFormulario(agendamento, secretaria);
            this.saveTransaction(agendamento);
        }
        else {
            throw new RuntimeException();
        }
    }
    @Transactional
    public void updateStatus(Long id, Agendamento agendamento){
        if (id == agendamento.getId()) {
            this.agendamentoRepository.updateStatus(LocalDateTime.now(), agendamento.getId());
        } else {
            throw new RuntimeException();
        }
    }
    @Transactional
    public void validarFormulario(Agendamento agendamento, Secretaria secretaria) {
        if (agendamento.getDataDe().compareTo(agendamento.getDataAte()) >= 0) {
            throw new RuntimeException("Warning: As datas são inválidas");
        }
        if(agendamento.getStatusAgendamento() == null){
            agendamento.setStatusAgendamento(StatusAgendamento.pendente);
        }
        if (secretaria == null) {
            agendamento.setStatusAgendamento(StatusAgendamento.aprovado.pendente);
        }
    }
}


