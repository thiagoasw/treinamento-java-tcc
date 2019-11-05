package com.totvs.tjc.infra.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.totvs.tjc.app.EmprestimoApplicationService;
import com.totvs.tjc.app.EmprestimoCommands.AprovarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.QuitarEmprestimo;
import com.totvs.tjc.app.EmprestimoCommands.RecusarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.SolicitarEmprestimo;
import com.totvs.tjc.emprestimo.EmprestimoId;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/emprestimos", produces = APPLICATION_JSON_UTF8_VALUE)
public class EmprestimoController {

    private final EmprestimoApplicationService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<EmprestimoId> solicitarEmprestimo(@RequestBody SolicitarEmprestimo cmd) {
        
        EmprestimoId id = service.handle(cmd);
        
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .path(id.toString())
            .build().toUri())
        .build();
    }

    @PutMapping(path = "/{id}/aprovar")
    void aprovarEmprestimo(@PathVariable String id) {
        service.handle(AprovarEmprestimoPendente.from(EmprestimoId.from(id)));
    }

    @PutMapping(path = "/{id}/recusar", consumes = APPLICATION_JSON_UTF8_VALUE)
    void recusarEmprestimo(@PathVariable String id, @RequestBody String motivo) {
        service.handle(RecusarEmprestimoPendente.of(EmprestimoId.from(id), motivo));
    }
    
    @PutMapping(path = "/{id}/quitar")
    void quitarEmprestimo(@PathVariable String id) {
        service.handle(QuitarEmprestimo.from(EmprestimoId.from(id)));
    }
    
}
