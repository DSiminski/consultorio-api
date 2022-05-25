package br.com.consultorio.controller;

import br.com.consultorio.entity.Especialidade;
import br.com.consultorio.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class EspecialidadeController {

    @Autowired
    EspecialidadeService especialidadeService;

    @GetMapping("/api/especialidade")
    public ResponseEntity<Especialidade> findById(
            @PathVariable("idEspecialidade") Long idEspecialidade
    ) {
        return ResponseEntity.ok().body(this.especialidadeService.findById(idEspecialidade).get());
    }

    @GetMapping
    public ResponseEntity<Page<Especialidade>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.especialidadeService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Especialidade especialidade){
        try {
            this.especialidadeService.insert(especialidade);
            return ResponseEntity.ok().body("Especialidade cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idEspecialidade}")
    public ResponseEntity<?> update(
            @RequestBody Especialidade especialidade,
            @PathVariable Long idEspecialidade
    ){
        try {
            this.especialidadeService.update(idEspecialidade, especialidade);
            return ResponseEntity.ok().body("Especialidade cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/status/{idEspecialidade}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Especialidade especialidade,
            @PathVariable Long idEspecialidade
    ){
        try {
            this.especialidadeService.update(idEspecialidade, especialidade);
            return ResponseEntity.ok().body("Especialidade desativada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}