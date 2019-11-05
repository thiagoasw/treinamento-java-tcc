package com.totvs.tjc.app.carteira;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.totvs.tjc.carteira.CarteiraRepository;
import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.ContaId;

public class CarteiraDomainRepositoryMock implements CarteiraRepository {

    private final Map<ContaId, Conta> map = new HashMap<>();

    @Override
    public void save(Conta conta) {
        map.put(conta.getId(), conta);
    }

    @Override
    public Conta getOne(ContaId id) {
        return map.get(id);
    }

    @Override
    public boolean existsByEmpresaCnpj(Cnpj cnpj) {
        return map.entrySet().stream()
            .anyMatch(e -> e.getValue().getEmpresa().getCnpj().equals(cnpj));
    }

    @Override
    public Optional<Conta> findOneByEmpresaCnpj(Cnpj cnpj) {
        
        Optional<Entry<ContaId, Conta>> find = map.entrySet().stream()
            .filter(e -> e.getValue().getEmpresa().getCnpj().equals(cnpj))
            .findFirst();
        
        if (find.isEmpty()) 
            return Optional.empty();
        
        return Optional.of(find.get().getValue());
    }

}
