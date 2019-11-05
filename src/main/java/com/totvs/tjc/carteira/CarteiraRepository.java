package com.totvs.tjc.carteira;

import java.util.Optional;

public interface CarteiraRepository {

    void save(Conta conta);

    Conta getOne(ContaId id);

    boolean existsByEmpresaCnpj(Cnpj cnpj);
    
    Optional<Conta> findOneByEmpresaCnpj(Cnpj cnpj);

}
