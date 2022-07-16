package br.com.consultorio.service;

import br.com.consultorio.entity.*;
import br.com.consultorio.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    public AgendamentoRepository agendamentoRepository;

    @Autowired
    public HistoricoService historicoService;

    public void insert(Agendamento agendamento) {
        this.validarInsert(agendamento);
        this.saveTransaction(agendamento);
        this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                agendamento.getPaciente(),agendamento.getSecretaria(),agendamento.getMedico());
    }

    public boolean dataMaiorAtual(LocalDateTime data){
        return data.compareTo(LocalDateTime.now()) >= 0 ? true : false;
    }

    public boolean dataMenorIgualAtual(LocalDateTime data){
        return data.compareTo(LocalDateTime.now()) <= 0 ? true : false;
    }

    public boolean dataDeMaiorDataAte(LocalDateTime dataDe, LocalDateTime dataAte){
        return dataAte.compareTo(dataDe) >= 0 ? true : false;
    }

    public boolean horarioComercial(LocalDateTime data){
        if ((data.getHour() >= 8 && data.getHour() < 12)
                || (data.getHour() >= 14 && data.getHour() < 18)
        ){
            return true;
        } else {
            return false;
        }
    }

    public boolean diaSemana(LocalDateTime data){
        return data.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || data.getDayOfWeek().equals(DayOfWeek.SUNDAY) ? false : true;
    }

    public boolean horarioMedico(Agendamento agendamento){
        return this.agendamentoRepository.conflitoMedicoPaciente(
                agendamento.getId(),
                agendamento.getDataDe(),
                agendamento.getDataAte(),
                agendamento.getMedico().getId(),
                agendamento.getPaciente().getId()).size() > 0 ? false : true;
    }

    public void validarEncaixeTrue(Agendamento agendamento) {
        Assert.isTrue(this.dataMaiorAtual(agendamento.getDataAte()),
                "Warning: Não pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataMaiorAtual(agendamento.getDataDe()),
                "Warning: Não pode ser agendado uma consulta no passado");
        Assert.isTrue(this.dataDeMaiorDataAte(agendamento.getDataDe(),agendamento.getDataAte()),
                "Warning: Data inválida");
        Assert.isTrue(this.horarioComercial(agendamento.getDataDe()),
                "Warning: Não pode ser agendado uma consulta fora do horário comercial");
        Assert.isTrue(this.horarioComercial(agendamento.getDataAte()),
                "Warning: Não pode ser agendado uma consulta fora do horário comercial");
        Assert.isTrue(this.diaSemana(agendamento.getDataDe()),
                "Warning: Não pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.diaSemana(agendamento.getDataAte()),
                "Warning: Não pode ser agendado uma consulta no final de semana");
        Assert.isTrue(this.horarioMedico(agendamento),
                "Warning: O horário do agendamento está ocupado");
    }

    public void validarEncaixeFalse(Agendamento agendamento) {
        Assert.isTrue(this.horarioMedico(agendamento),
                "Warning: O horário do agendamento está ocupado");
    }

    public void validarInsert(Agendamento agendamento) {

        if (agendamento.getSecretaria() == null) {
            if(agendamento.getEncaixe()){
                this.validarEncaixeTrue(agendamento);
                agendamento.setStatusAgendamento(StatusAgendamento.pendente);
            } else {
                this.validarEncaixeFalse(agendamento);
                agendamento.setStatusAgendamento(StatusAgendamento.pendente);
            }
        } else {
            if(agendamento.getEncaixe()){
                this.validarEncaixeTrue(agendamento);
                agendamento.setStatusAgendamento(StatusAgendamento.aprovado);
            } else {
                this.validarEncaixeFalse(agendamento);
                agendamento.setStatusAgendamento(StatusAgendamento.aprovado);
            }
        }

    }

    @Transactional
    public void saveTransaction(Agendamento agendamento) {
        this.agendamentoRepository.save(agendamento);
    }

    public Optional<Agendamento> findById(Long id) {
        return this.agendamentoRepository.findById(id);
    }

    public Page<Agendamento> listAll(Pageable pageable) {
        return this.agendamentoRepository.findAll(pageable);
    }

    public void update(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdate(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdate(Agendamento agendamento) {
        if(agendamento.getEncaixe()){
            this.validarEncaixeTrue(agendamento);
        } else {
            this.validarEncaixeFalse(agendamento);
        }
    }

    public void updateStatusAprovado(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdateAprovado(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateAprovado(Agendamento agendamento) {
        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(agendamento.getSecretaria() != null){
                agendamento.setStatusAgendamento(StatusAgendamento.aprovado);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Aprovado");
        }
    }

    public void updateStatusRejeitado(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdateRejeitado(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateRejeitado(Agendamento agendamento) {
        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.pendente)) {
            if(agendamento.getSecretaria() != null){
                agendamento.setStatusAgendamento(StatusAgendamento.rejeitado);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Rejeitado");
        }
    }

    public void updateStatusCancelado(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdateCancelado(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCancelado(Agendamento agendamento) {
        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.pendente)
                || agendamento.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            agendamento.setStatusAgendamento(StatusAgendamento.rejeitado);
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Cancelado");
        }
    }

    public void updateStatusCompareceu(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdateCompareceu(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateCompareceu(Agendamento agendamento) {
        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(agendamento.getSecretaria() != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agendamento.getDataAte()),
                        "Warning: Data inválida");
                Assert.isTrue(this.dataMenorIgualAtual(agendamento.getDataDe()),
                        "Warning: Data inválida");
                agendamento.setStatusAgendamento(StatusAgendamento.compareceu);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Compareceu");
        }
    }

    public void updateStatusNCompareceu(Long id,Agendamento agendamento) {
        if (id == agendamento.getId()) {
            this.validarUpdateNCompareceu(agendamento);
            this.saveTransaction(agendamento);
            this.historicoService.createHistorico(agendamento,agendamento.getStatusAgendamento(),LocalDateTime.now(),
                    agendamento.getPaciente(), agendamento.getSecretaria(),agendamento.getMedico());
        } else {
            throw new RuntimeException();
        }
    }

    public void validarUpdateNCompareceu(Agendamento agendamento) {
        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.aprovado)) {
            if(agendamento.getSecretaria() != null) {
                Assert.isTrue(this.dataMenorIgualAtual(agendamento.getDataAte()),
                        "Warning: Data inválida");
                Assert.isTrue(this.dataMenorIgualAtual(agendamento.getDataDe()),
                        "Warning: Data inválida");
                agendamento.setStatusAgendamento(StatusAgendamento.ncompareceu);
            } else {
                throw new RuntimeException("Warning: Sem permissão para executar essa ação");
            }
        } else {
            throw new RuntimeException("Warning: Status inválido para update de Não Compareceu");
        }
    }

    @Transactional
    public void disable(Long id, Agendamento agendamento) {
        if (id == agendamento.getId()){
            this.agendamentoRepository.updateStatus(agendamento.getId());
        } else {
            throw new RuntimeException();
        }
    }
    public Page<Agendamento> findAllToday(Pageable pageable) {
        return this.agendamentoRepository.findAllToday(pageable);
    }
}



