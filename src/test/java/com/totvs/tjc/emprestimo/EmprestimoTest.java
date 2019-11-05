package com.totvs.tjc.emprestimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.Cpf;
import com.totvs.tjc.carteira.Empresa;
import com.totvs.tjc.carteira.Responsavel;
import com.totvs.tjc.emprestimo.Emprestimo.Situacao;

@DisplayName("Teste de emprestimo")
public class EmprestimoTest {

    private static Conta conta;
    
    private static MonetaryAmount valor;
    
    @BeforeAll
    static void initAll() {
        
        valor = Money.of(10, "BRL");
        
        conta = Conta.from(Empresa.builder("Valasso SA", Cnpj.from("23.964.134/0001-28"))
            .responsavel(Responsavel.of("Rosevaldo Braga", Cpf.from("004.574.359-25")))
            .funcionarios(50)
            .valorMercado(Money.of(500000, "BRL"))
        .build());
    }

    @Test
    @DisplayName("Caminho feliz")
    void emprestimoAbertoComoPendente() {

        Emprestimo emprestimo = (Emprestimo) Emprestimo.of(conta, valor);

        assertNotNull(emprestimo);
        assertNotNull(emprestimo.getId());
        assertEquals(conta, emprestimo.getConta());
        assertEquals(valor, emprestimo.getValor());
        assertEquals(Situacao.PENDENTE, emprestimo.getSituacao());

        assertNull(emprestimo.getObservacao());
        assertNull(emprestimo.getQuitadoEm());
        assertNull(emprestimo.getLiberadoEm());
        
        assertTrue(emprestimo.getSolicitadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getUltimaMovimentacaoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
    }
    
    @Test
    @DisplayName("Caminho feliz com status negado na criação")
    void emprestimoAbertoComoNegado() {

        Emprestimo emprestimo = (Emprestimo) Emprestimo.of(conta, Money.of(100000, "BRL"));
        
        assertNull(emprestimo.getQuitadoEm());
        assertNull(emprestimo.getLiberadoEm());
        
        assertEquals(Situacao.NEGADO, emprestimo.getSituacao());
        assertEquals("Sem limite dispon\u00EDvel.", emprestimo.getObservacao());
        assertEquals(conta, emprestimo.getConta());
    }
    
    @Test
    @DisplayName("Emprestimo pendente, aceito")
    void aceitarEmprestimoPendente() {
        
        Emprestimo emprestimo = (Emprestimo) Emprestimo.of(conta, valor);
        
        emprestimo.aceitar();
        
        assertNull(emprestimo.getObservacao());
        assertNull(emprestimo.getQuitadoEm());
        
        assertEquals(Situacao.LIBERADO, emprestimo.getSituacao());
        assertTrue(emprestimo.getLiberadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getSolicitadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getUltimaMovimentacaoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertEquals(valor, emprestimo.getConta().getSaldoDevedor());
    }
    
    @Test
    @DisplayName("Emprestimo pendente, recusado")
    void recusarEmprestimoPendente() {
        
        String motivo = "Teste";
        Emprestimo emprestimo = (Emprestimo) Emprestimo.of(conta, valor);
        
        emprestimo.recusar(motivo);
        
        assertNull(emprestimo.getQuitadoEm());
        assertNull(emprestimo.getLiberadoEm());
        
        assertEquals(Situacao.NEGADO, emprestimo.getSituacao());
        assertEquals(motivo, emprestimo.getObservacao());
        
        assertTrue(emprestimo.getSolicitadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getUltimaMovimentacaoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        
        assertEquals(Conta.SALDO_INICIAL, emprestimo.getConta().getSaldoDevedor());
    }
    
    @Test
    @DisplayName("Emprestimo liberado, quitado")
    void quitarEmprestimoLiberado() {
        
        Emprestimo emprestimo = (Emprestimo) Emprestimo.of(conta, valor);
        
        emprestimo.aceitar();
        emprestimo.quitar();
        
        assertNull(emprestimo.getObservacao());
        
        assertEquals(Situacao.QUITADO, emprestimo.getSituacao());
        assertTrue(emprestimo.getSolicitadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getLiberadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getQuitadoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertTrue(emprestimo.getUltimaMovimentacaoEm().isBefore(LocalDateTime.now().plusSeconds(1l)));
        assertEquals(Conta.SALDO_INICIAL, emprestimo.getConta().getSaldoDevedor());
    }

}
