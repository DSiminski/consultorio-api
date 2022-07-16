package br.com.consultorio.service;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.entity.TipoAtendimento;
import br.com.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Optional<Paciente> findById(Long id){
        return this.pacienteRepository.findById(id);
    }

    public Page<Paciente> listAll(Pageable pageable){
        return this.pacienteRepository.findAll(pageable);
    }

    public void insert(Paciente paciente){
        validarFormulario(paciente);
        this.pacienteRepository.save(paciente);
    }
    @Transactional
    public void update(Long id, Paciente paciente){
        if (id == paciente.getId()) {
            this.validarFormulario(paciente);
            this.saveTransaction(paciente);
        }
        else {
            throw new RuntimeException();
        }
    }
    public void saveTransaction(Paciente paciente){
        this.pacienteRepository.save(paciente);
    }

    @Transactional
    public void updateStatus(Long id, Paciente paciente){
        if (id == paciente.getId()) {
            this.pacienteRepository.updateStatus(paciente.getId());
        }
        else {
            throw new RuntimeException();
        }
    }
    @Transactional
    public void validarFormulario(Paciente paciente) {
        if (paciente.getTipoAtendimento().equals(TipoAtendimento.convenio)) {
            if (paciente.getConvenio() == null || paciente.getConvenio().getId() == null) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Convenio não informado");
            }
            if (paciente.getNumeroCartaoConvenio() == null) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Cartão não informado");
            }
            if (paciente.getDataVencimento() == null) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Data de Vencimento do Cartão não informado");
            }
            if (paciente.getDataVencimento().isBefore(LocalDateTime.now()) ) {
                throw new RuntimeException("Tipo Atendimento = Convenio. Data de Vencimento do Cartão não expirado");
            }
        }

        if(paciente.getTipoAtendimento().equals(TipoAtendimento.particular)){
            paciente.setConvenio(null);
            paciente.setNumeroCartaoConvenio(null);
            paciente.setDataVencimento(null);
        }

        this.pacienteRepository.save(paciente);

    }
}

