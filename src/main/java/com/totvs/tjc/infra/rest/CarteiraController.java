package com.totvs.tjc.infra.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.totvs.tjc.app.CarteiraApplicationService;
import com.totvs.tjc.app.CarteiraCommands.AbrirConta;
import com.totvs.tjc.carteira.ContaId;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/carteiras", produces = APPLICATION_JSON_UTF8_VALUE)
public class CarteiraController {

    private final CarteiraApplicationService service;
    
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Void> abrirConta(@RequestBody AbrirConta cmd) {
        
        ContaId id = service.handle(cmd);
        
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/")
                .path(id.toString())
            .build().toUri())
        .build();
    }

}
