package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teste de contas")
public class ContaTest {

    private static Empresa empresa;

    @BeforeAll
    static void initAll() {
        empresa = Empresa.builder("Valasso SA", Cnpj.from("23.964.134/0001-28"))
            .responsavel(Responsavel.of("Rosevaldo Braga", Cpf.from("004.574.359-25")))
            .funcionarios(50)
            .valorMercado(FastMoney.of(500000, "BRL"))
            .build();
    }

    @Test
    @DisplayName("Caminho feliz")
    void construirEmpresaDeveFuncionar() {

        Conta conta = Conta.from(empresa);

        assertNotNull(conta);
        assertNotNull(conta.getId());
        assertEquals(Conta.SALDO_INICIAL, conta.getSaldoDevedor());
        assertEquals(empresa, conta.getEmpresa());
        assertEquals(FastMoney.of(10000, "BRL"), conta.getLimite());
    }

    @Test
    @DisplayName("Sem empresa")
    void contaSemEmpresaDeveFalhar() {
        assertThrows(NullPointerException.class, () -> Conta.from(null));
    }

    @Test
    @DisplayName("Com limite disponivel")
    void contaComLimiteDisponivel() {
        Conta conta = Conta.from(empresa);
        assertTrue(conta.hasLimiteDisponivel(FastMoney.of(10, "BRL")));
        assertTrue(conta.hasLimiteDisponivel(FastMoney.of(5000, "BRL")));
        assertTrue(conta.hasLimiteDisponivel(FastMoney.of(10000, "BRL")));
    }
    
    @Test
    @DisplayName("Sem limite disponivel")
    void contaSemLimiteDisponivel() {
        Conta conta = Conta.from(empresa);
        assertFalse(conta.hasLimiteDisponivel(FastMoney.of(50000, "BRL")));
        assertFalse(conta.hasLimiteDisponivel(FastMoney.of(10001, "BRL")));
    }

    @Test
    @DisplayName("Debitar saldo devedor")
    void debitarSaldoDevedor() {
        
        MonetaryAmount valor = FastMoney.of(5000, "BRL");
        Conta conta = Conta.from(empresa);
        
        conta.debitar(valor);
        
        assertNotEquals(valor, conta.getSaldoDevedor());
    }
    
    @Test
    @DisplayName("Creditar saldo devedor")
    void creditarSaldoDevedor() {
        
        MonetaryAmount valor = FastMoney.of(5000, "BRL");
        Conta conta = Conta.from(empresa);
        
        conta.creditar(valor);
        
        assertEquals(valor, conta.getSaldoDevedor());
    }
}
