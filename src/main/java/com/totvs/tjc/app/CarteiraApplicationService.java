package com.totvs.tjc.app;

import static com.totvs.tjc.util.Preconditions.checkState;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.totvs.tjc.app.CarteiraCommands.AbrirConta;
import com.totvs.tjc.carteira.CarteiraRepository;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.ContaId;
import com.totvs.tjc.carteira.Empresa;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@Validated
public class CarteiraApplicationService {

    private final CarteiraRepository repository;

    @Transactional
    public ContaId handle(@Valid AbrirConta cmd) {

        // FIXME: Pode ser evitado retornando o código já existente, se possível.
        checkState(!repository.existsByEmpresaCnpj(cmd.getCnpj()));

        Empresa empresa = Empresa.builder(cmd.getName(), cmd.getCnpj())
            .funcionarios(cmd.getFuncionarios())
            .valorMercado(cmd.getValorMercado())
            .responsavel(cmd.getResponsavel())
        .build();

        Conta conta = Conta.from(empresa);
        
        repository.save(conta);
        
        return conta.getId();
    }

}
