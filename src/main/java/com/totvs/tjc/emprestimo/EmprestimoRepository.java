package com.totvs.tjc.emprestimo;

import java.util.Optional;

public interface EmprestimoRepository {

    void save(SituacaoEmprestimo.Solicitado emprestimo);
    
    Optional<SituacaoEmprestimo.Pendente> getEmprestimoPendente(EmprestimoId id);
    
    Optional<SituacaoEmprestimo.Liberado> getEmprestimoLiberado(EmprestimoId id);

}
