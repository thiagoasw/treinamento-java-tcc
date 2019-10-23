package com.totvs.tj.tcc.domain.conta;

public interface ContaRepository {

    void save(Conta conta);
    
    Conta getOne(ContaId id);
    
}
