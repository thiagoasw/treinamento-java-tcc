package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.emprestimo.EmprestimoId;

@DisplayName("Teste do identificador de conta")
public class ContaIdTest {

    @Test
    @DisplayName("Identificador de conta")
    void testeContaId() {
        ContaId id = ContaId.generate();
        assertEquals(id, ContaId.from(id.toString()));
        assertNotEquals(id, ContaId.generate());
        assertNotEquals(id, EmprestimoId.generate());
    }

}
