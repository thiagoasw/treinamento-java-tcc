package com.totvs.tj.tcc.domain.conta;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "from")
public class ContaId {

    private String value;

    /**
     * Retorna o valor raiz do códito.
     */
    @Override
    public String toString() {
        return value;
    }

    public static ContaId generate() {
        return ContaId.from(UUID.randomUUID().toString());
    }

}
