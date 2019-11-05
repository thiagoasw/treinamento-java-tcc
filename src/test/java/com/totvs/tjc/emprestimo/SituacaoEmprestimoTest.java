package com.totvs.tjc.emprestimo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.emprestimo.Emprestimo.Situacao;

@DisplayName("Teste do transição das situações de emprestimo")
public class SituacaoEmprestimoTest {

    @Test
    @DisplayName("Pendente")
    void pendente() {
        assertEquals(Situacao.NEGADO, Situacao.PENDENTE.deny());
        assertEquals(Situacao.LIBERADO, Situacao.PENDENTE.next());
    }
    
    @Test
    @DisplayName("Liberado")
    void liberado() {
        assertEquals(Situacao.NEGADO, Situacao.LIBERADO.deny());
        assertEquals(Situacao.QUITADO, Situacao.LIBERADO.next());
    }
    
    @Test
    @DisplayName("Quitado")
    void quitado() {
        assertEquals(Situacao.QUITADO, Situacao.QUITADO.deny());
        assertEquals(Situacao.QUITADO, Situacao.QUITADO.next());
    }
    
    @Test
    @DisplayName("Negado")
    void negado() {
        assertEquals(Situacao.NEGADO, Situacao.NEGADO.deny());
        assertEquals(Situacao.NEGADO, Situacao.NEGADO.next());
    }

}
