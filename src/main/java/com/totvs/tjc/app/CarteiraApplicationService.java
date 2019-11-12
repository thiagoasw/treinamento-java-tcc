package com.totvs.tjc.app;

import static com.totvs.tjc.carteira.FalhasPrevistas.CNPJ_JA_POSSUI_CONTA;
import static com.totvs.tjc.infra.CommandFailure.from;
import static com.totvs.tjc.infra.Result.failure;
import static com.totvs.tjc.infra.Result.success;
import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

import javax.transaction.Transactional;
import javax.validation.Validator;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import com.totvs.tjc.app.CarteiraCommands.AbrirConta;
import com.totvs.tjc.carteira.CarteiraRepository;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.ContaId;
import com.totvs.tjc.carteira.Empresa;
import com.totvs.tjc.infra.Failure;
import com.totvs.tjc.infra.Result;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarteiraApplicationService {

    private final Validator validator;

    private final CarteiraRepository repository;

    @Transactional  
    @Lock(PESSIMISTIC_WRITE)
    public Result<ContaId, Failure> handle(AbrirConta cmd) {

        var violations = validator.validate(cmd);

        if (violations.isEmpty() == false)
            return failure(from(violations));

        if (repository.existsByEmpresaCnpj(cmd.getCnpj()) == true)
            return failure(CNPJ_JA_POSSUI_CONTA);

        Empresa empresa = Empresa.builder(cmd.getName(), cmd.getCnpj())
            .funcionarios(cmd.getFuncionarios())
            .valorMercado(cmd.getValorMercado())
            .responsavel(cmd.getResponsavel())
        .build();

        Conta conta = repository.save(Conta.from(empresa));
        
        return success(conta.getId());
    }

}
