package com.totvs.tjc.app;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.totvs.tjc.app.EmprestimoCommands.AprovarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.QuitarEmprestimo;
import com.totvs.tjc.app.EmprestimoCommands.RecusarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.SolicitarEmprestimo;
import com.totvs.tjc.carteira.CarteiraRepository;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.emprestimo.Emprestimo;
import com.totvs.tjc.emprestimo.EmprestimoId;
import com.totvs.tjc.emprestimo.EmprestimoRepository;
import com.totvs.tjc.emprestimo.SituacaoEmprestimo;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
@Validated
public class EmprestimoApplicationService {

    private final EmprestimoRepository repository;

    private final CarteiraRepository contaRepository;

    @Transactional
    public EmprestimoId handle(@Valid SolicitarEmprestimo cmd) {

        Optional<Conta> holder = contaRepository.findOneByEmpresaCnpj(cmd.getCnpj());

        if (holder.isEmpty()) {
            // TODO: Retornar a mensagem de resposta corretamente...
            return null;
        }

        SituacaoEmprestimo.Solicitado emprestimo = Emprestimo.of(holder.get(), cmd.getValor());

        repository.save(emprestimo);
        
        return emprestimo.getId();
    }

    @Transactional
    public void handle(@Valid AprovarEmprestimoPendente cmd) {

        Optional<SituacaoEmprestimo.Pendente> holder = repository.getEmprestimoPendente(cmd.getId());

        if (holder.isEmpty()) {
            // TODO: Retornar a mensagem de resposta corretamente...
            return;
        }

        SituacaoEmprestimo.Pendente emprestimo = holder.get();

        emprestimo.aceitar();

        repository.save(emprestimo);
    }

    @Transactional
    public void handle(@Valid RecusarEmprestimoPendente cmd) {

        Optional<SituacaoEmprestimo.Pendente> holder = repository.getEmprestimoPendente(cmd.getId());

        if (holder.isEmpty()) {
            // TODO: Retornar a mensagem de resposta corretamente...
            return;
        }

        SituacaoEmprestimo.Pendente emprestimo = holder.get();

        emprestimo.recusar(cmd.getMotivo());

        repository.save(emprestimo);
    }

    @Transactional
    public void handle(@Valid QuitarEmprestimo cmd) {

        Optional<SituacaoEmprestimo.Liberado> holder = repository.getEmprestimoLiberado(cmd.getId());

        if (holder.isEmpty()) {
            // TODO: Retornar a mensagem de resposta corretamente...
            return;
        }

        SituacaoEmprestimo.Liberado emprestimo = holder.get();

        emprestimo.quitar();

        repository.save(emprestimo);
    }

}
