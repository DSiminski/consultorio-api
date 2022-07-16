package br.com.consultorio.controller;

import br.com.consultorio.entity.Agendamento;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000" )
@Controller
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping("/{idAgendamento}")
    public ResponseEntity<Agendamento> findById(
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        return ResponseEntity.ok().body(this.agendamentoService.findById(idAgendamento).get());
    }

    @GetMapping
    public ResponseEntity<Page<Agendamento>> listByAllPage(Pageable pageable){
        return ResponseEntity.ok().body(this.agendamentoService.listAll(pageable));
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Agendamento agendamento){
        try {
            this.agendamentoService.insert(agendamento);
            return ResponseEntity.ok().body("Agenda Cadastrada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{idAgendamento}")
    public ResponseEntity<?> update(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.update(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Agenda Atualizada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/aprovado/{idAgendamento}")
    public ResponseEntity<?> updateStatusAprovado(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.updateStatusAprovado(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Agenda Aprovada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/rejeitado/{idAgendamento}")
    public ResponseEntity<?> updateStatusRejeitado(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.updateStatusRejeitado(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Agenda Rejeitada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/status/compareceu/{idAgendamento}")
    public ResponseEntity<?> updateStatusCompareceu(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.updateStatusCompareceu(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Status Da Agenda Alterado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/ncompareceu/{idAgendamento}")
    public ResponseEntity<?> updateStatusNCompareceu(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.updateStatusNCompareceu(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Status Da Agenda Alterado Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/cancelado/{idAgendamento}")
    public ResponseEntity<?> updateStatusCancelado(
            @RequestBody Agendamento agendamento,
            @PathVariable("idAgendamento") Long idAgendamento
    ){
        try {
            this.agendamentoService.updateStatusCancelado(idAgendamento,agendamento);
            return ResponseEntity.ok().body("Agenda Cancelada Com Sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/today")
    public ResponseEntity<Page<Agendamento>> listByAllPageToday(Pageable pageable){
        return ResponseEntity.ok().body(this.agendamentoService.findAllToday(pageable));
    }
}
