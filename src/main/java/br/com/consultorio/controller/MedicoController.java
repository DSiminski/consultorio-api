package br.com.consultorio.controller;
import br.com.consultorio.entity.Medico;
import br.com.consultorio.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000" )
@Controller
@RequestMapping("/api/medicos")
public class MedicoController {
    @Autowired
    MedicoService medicoService;

    @GetMapping("/{idMedico}")
    public ResponseEntity<Medico> findById(
            @PathVariable("idMedico") Long idMedico
    ) {
        return ResponseEntity.ok().body(this.medicoService.findById(idMedico).get());
    }

    @GetMapping
    public ResponseEntity<Page<Medico>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.medicoService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Medico medico){
        try {
            this.medicoService.insert(medico);
            return ResponseEntity.ok().body("Medico cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idMedico}")
    public ResponseEntity<?> update(
            @RequestBody Medico medico,
            @PathVariable Long idMedico
    ){
        try {
            this.medicoService.update(idMedico, medico);
            return ResponseEntity.ok().body("Medico cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/disable/{idMedico}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Medico medico,
            @PathVariable Long idMedico
    ){
        try {
            this.medicoService.updateStatus(idMedico, medico);
            return ResponseEntity.ok().body("Medico desabilitado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
