package com.totvs.tjc.infra.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.totvs.tjc.emprestimo.Emprestimo;
import com.totvs.tjc.emprestimo.Emprestimo.Situacao;
import com.totvs.tjc.emprestimo.EmprestimoId;
import com.totvs.tjc.emprestimo.EmprestimoRepository;
import com.totvs.tjc.emprestimo.SituacaoEmprestimo;

@Repository
public interface EmprestimoRepositoryJpa extends EmprestimoRepository, JpaRepository<Emprestimo, EmprestimoId> {

    Emprestimo findByIdAndSituacao(EmprestimoId id, Situacao situacao);
    
    @Override
    default Optional<SituacaoEmprestimo.Pendente> getEmprestimoPendente(EmprestimoId id) {
        return Optional.ofNullable(findByIdAndSituacao(id, Situacao.PENDENTE));
    }

    @Override
    default Optional<SituacaoEmprestimo.Liberado> getEmprestimoLiberado(EmprestimoId id) {
        return Optional.ofNullable(findByIdAndSituacao(id, Situacao.LIBERADO));
    }

}