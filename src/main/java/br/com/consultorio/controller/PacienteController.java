package br.com.consultorio.controller;
import br.com.consultorio.entity.Paciente;
import br.com.consultorio.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000" )
@Controller
@RequestMapping("/api/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> findById(
            @PathVariable("idPaciente") Long idPaciente
    ) {
        return ResponseEntity.ok().body(this.pacienteService.findById(idPaciente).get());
    }

    @GetMapping
    public ResponseEntity<Page<Paciente>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.pacienteService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Paciente paciente){
        try {
            this.pacienteService.insert(paciente);
            return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idPaciente}")
    public ResponseEntity<?> update(
            @RequestBody Paciente paciente,
            @PathVariable Long idPaciente
    ){
        try {
            this.pacienteService.update(idPaciente, paciente);
            return ResponseEntity.ok().body("Paciente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/disable/{idPaciente}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Paciente paciente,
            @PathVariable Long idPaciente
    ){
        try {
            this.pacienteService.updateStatus(idPaciente, paciente);
            return ResponseEntity.ok().body("Paciente desabilitado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
