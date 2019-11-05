package com.totvs.tjc.emprestimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.ContaId;

@DisplayName("Teste do identificador de emprestimo")
public class EmprestimoIdTest {

    @Test
    @DisplayName("Identificador de emprestimo")
    void testeEmprestimoId() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, EmprestimoId.from(id.toString()));
        assertNotEquals(id, EmprestimoId.generate());
        assertNotEquals(id, ContaId.generate());
    }

}
