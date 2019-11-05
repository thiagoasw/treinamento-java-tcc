package com.totvs.tjc.app.emprestimo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.totvs.tjc.emprestimo.Emprestimo;
import com.totvs.tjc.emprestimo.Emprestimo.Situacao;
import com.totvs.tjc.emprestimo.EmprestimoId;
import com.totvs.tjc.emprestimo.EmprestimoRepository;
import com.totvs.tjc.emprestimo.SituacaoEmprestimo;
import com.totvs.tjc.emprestimo.SituacaoEmprestimo.Liberado;
import com.totvs.tjc.emprestimo.SituacaoEmprestimo.Pendente;

public class EmprestimoDomainRepositoryMock implements EmprestimoRepository {

    private final Map<EmprestimoId, Emprestimo> map = new HashMap<>();

    @Override
    public void save(SituacaoEmprestimo.Solicitado emprestimo) {
        map.put(emprestimo.getId(), (Emprestimo) emprestimo);
    }

    @Override
    public Optional<Pendente> getEmprestimoPendente(EmprestimoId id) {
        return Optional.ofNullable(findByIdAndSituacao(id, Situacao.PENDENTE).orElseGet(() -> {
            return null;
        }));
    }

    @Override
    public Optional<Liberado> getEmprestimoLiberado(EmprestimoId id) {
        return Optional.ofNullable(findByIdAndSituacao(id, Situacao.LIBERADO).orElseGet(() -> null));
    }
    
    public Emprestimo getOne(EmprestimoId id) {
        return map.get(id);
    }

    private Optional<Emprestimo> findByIdAndSituacao(EmprestimoId id, Situacao situacao) {

        Emprestimo emprestimo = map.get(id);

        if (emprestimo == null)
            return Optional.empty();

        if (emprestimo.getSituacao().equals(situacao))
            return Optional.of(emprestimo);

        return Optional.empty();
    }
}
