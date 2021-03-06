package br.com.consultorio.service;

import br.com.consultorio.entity.Especialidade;
import br.com.consultorio.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service //bean = gerenciada pelo Spring Boot;
public class EspecialidadeService {
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    /**
     *
     * @param id
     * @return
     */
    public Optional<Especialidade> findById(Long id){
        return this.especialidadeRepository.findById(id);
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Especialidade> listAll(Pageable pageable){
        return this.especialidadeRepository.findAll(pageable);
    }

    /**
     *
     * @param id
     * @param especialidade
     */
    @Transactional
    public void update(Long id, Especialidade especialidade){
        if (id == especialidade.getId()) {
            this.especialidadeRepository.save(especialidade);
        }
        else {
            throw new RuntimeException();
        }
    }
    /**
     *
     * @param especialidade
     */
    @Transactional
    public void insert(Especialidade especialidade){
        this.especialidadeRepository.save(especialidade);
    }
    /**
     *
     * @param id
     * @param especialidade
     */
    @Transactional
    public void updateStatus(Long id, Especialidade especialidade){
        if (id == especialidade.getId()) {
            this.especialidadeRepository.updateStatus(especialidade.getId());
        }
        else {
            throw new RuntimeException();
        }
    }

}
