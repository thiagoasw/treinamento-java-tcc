package com.totvs.tjc.carteira;

import static com.totvs.tjc.carteira.PoliticaLimiteAberturaConta.POLITICA_MEI;
import static com.totvs.tjc.carteira.PoliticaLimiteAberturaConta.POLITICA_PADRAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.Empresa.Builder;

@DisplayName("Teste de politicas de abertura de contas")
public class PoliticaLimiteAberturaContaTest {

    private static Builder builderEmpresa = Empresa.builder("Valasso SA", Cnpj.from("23.964.134/0001-28"))
        .responsavel(Responsavel.of("Rosevaldo Braga", Cpf.from("004.574.359-25")));

    @Nested
    @DisplayName("Teste de politicas de abertura de contas: POLITICA_PADRAO")
    static class Padrao {

        @Test
        @DisplayName("Dentro do limite")
        void limiteDentroDoPadrao() {
            Empresa empresa = builderEmpresa.funcionarios(50).valorMercado(FastMoney.of(50000, "BRL")).build();
            assertTrue(POLITICA_PADRAO.calcularLimiteInicial(empresa).isLessThanOrEqualTo(POLITICA_PADRAO.getLimiteMaximo()));
        }

        @Test
        @DisplayName("Calculo do limite")
        void limiteCorretoPadrao() {
            Empresa empresa = builderEmpresa.funcionarios(50).valorMercado(FastMoney.of(50000, "BRL")).build();
            assertEquals(FastMoney.of(1000, "BRL"), POLITICA_PADRAO.calcularLimiteInicial(empresa));
        }

        @Test
        @DisplayName("Limite dentro do padrão para empresas gigantes")
        void limiteDentroPadraoParaGrandesEmpresas() {
            Empresa empresa = builderEmpresa.funcionarios(50).valorMercado(FastMoney.of(5000000, "BRL")).build();
            assertEquals(POLITICA_PADRAO.getLimiteMaximo(), POLITICA_PADRAO.calcularLimiteInicial(empresa));
        }

    }

    @Nested
    @DisplayName("Teste de politicas de abertura de contas: POLITICA_MEI")
    static class Mei {

        @Test
        @DisplayName("Dentro do limite")
        void limiteDentroDoPadrao() {
            Empresa empresa = builderEmpresa.funcionarios(50).valorMercado(FastMoney.of(50000, "BRL")).build();
            assertTrue(POLITICA_MEI.calcularLimiteInicial(empresa).isLessThanOrEqualTo(POLITICA_MEI.getLimiteMaximo()));
        }

        @Test
        @DisplayName("Calculo do limite para pequenas empresas")
        void limiteCorretoPadrao() {
            Empresa empresa = builderEmpresa.funcionarios(1).valorMercado(FastMoney.of(1, "BRL")).build();
            assertTrue(POLITICA_MEI.calcularLimiteInicial(empresa).isLessThanOrEqualTo(POLITICA_MEI.getLimiteMaximo()));
        }

        @Test
        @DisplayName("Limite dentro do padrão para empresas gigantes")
        void limiteDentroPadraoParaGrandesEmpresas() {
            Empresa empresa = builderEmpresa.funcionarios(1000).valorMercado(FastMoney.of(600000, "BRL")).build();
            assertTrue(POLITICA_MEI.calcularLimiteInicial(empresa).isLessThanOrEqualTo(POLITICA_MEI.getLimiteMaximo()));
        }

    }

}
