package com.totvs.tjc.infra.rest;

import static java.util.Collections.emptyMap;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.totvs.tjc.app.CarteiraApplicationService;
import com.totvs.tjc.app.CarteiraCommands.AbrirConta;
import com.totvs.tjc.infra.CommandFailure;
import com.totvs.tjc.infra.rest.advice.ExceptionResponse;
import com.totvs.tjc.infra.rest.advice.ExceptionResponse.ConstraintViolationResponse;
import com.totvs.tjc.infra.rest.advice.ExceptionResponse.ConstraintViolationResponse.ConstraintViolationResponseBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping(path = "/api/carteiras", produces = APPLICATION_JSON_UTF8_VALUE)
public class CarteiraController {

    private final CarteiraApplicationService service;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<?> abrirConta(@RequestBody AbrirConta cmd) {
        return service.handle(cmd).mapSuccessOrFailure(id -> accepted()
            .location(fromCurrentRequest()
                .path("/").path(id.toString()).build(emptyMap()))
            .build(),
            failure -> { 
                if (failure instanceof CommandFailure<?>) {
                    
                    ConstraintViolationResponseBuilder builder = ConstraintViolationResponse.builder();
                    
                    builder.operation(ExceptionResponse.of("AbrirConta", "Deu ruim"));
                    
                    ((CommandFailure<?>) failure).getViolations().stream()
                        .map(ExceptionResponse::from)
                        .forEach(builder::detail);
                    
                    return badRequest().body(builder.build());
                }
                return badRequest().body(failure); 
            }).get();
    }
}
