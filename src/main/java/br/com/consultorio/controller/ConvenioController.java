package br.com.consultorio.controller;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000" )
@Controller
@RequestMapping("/api/convenios")
public class ConvenioController {
    @Autowired
    ConvenioService convenioService;

    @GetMapping("/{idConvenios}")
    public ResponseEntity<Convenio> findById(
            @PathVariable("idConvenios") Long idConvenios
    ) {
        return ResponseEntity.ok().body(this.convenioService.findById(idConvenios).get());
    }

    @GetMapping
    public ResponseEntity<Page<Convenio>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.convenioService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Convenio convenio){
        try {
            this.convenioService.insert(convenio);
            return ResponseEntity.ok().body("Convenio cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{idConvenio}")
    public ResponseEntity<?> update(
            @RequestBody Convenio convenio,
            @PathVariable Long idConvenio
    ){
        try {
            this.convenioService.update(idConvenio, convenio);
            return ResponseEntity.ok().body("Convenio cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/disable/{idConvenio}")
    public ResponseEntity<?> updateStatus(
            @RequestBody Convenio convenio,
            @PathVariable Long idConvenio
    ){
        try {
            this.convenioService.updateStatus(idConvenio, convenio);
            return ResponseEntity.ok().body("Convenio desativado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
