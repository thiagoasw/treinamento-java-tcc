package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teste de empresa")
public class EmpresaTest {

    private static String razaoSocial;
    private static Cnpj cnpj;
    private static int funcionarios;
    private static MonetaryAmount valorMercado;

    private static Responsavel responsavel;

    private static Empresa.Builder builder;

    @BeforeAll
    static void initAll() {

        razaoSocial = "Candura LTDA";
        cnpj = Cnpj.from("23.964.134/0001-28");
        funcionarios = 100;
        valorMercado = FastMoney.of(9800000, "BRL");

        responsavel = Responsavel.of("Joviano Antunes", Cpf.from("004.574.359-25"));
    }

    @BeforeEach
    void init() {
        builder = Empresa.builder(razaoSocial, cnpj)
            .responsavel(responsavel)
            .funcionarios(funcionarios)
            .valorMercado(valorMercado);
    }

    @Test
    @DisplayName("Caminho feliz")
    void construirEmpresaDeveFuncionar() {

        Empresa empresa = builder.build();

        assertEquals(razaoSocial, empresa.getRazaoSocial());
        assertEquals(cnpj, empresa.getCnpj());
        assertEquals(funcionarios, empresa.getFuncionarios());
        assertEquals(valorMercado, empresa.getValorMercado());
        assertEquals(responsavel, empresa.getResponsavel());
    }

    @Test
    @DisplayName("Sem nome")
    void empresaSemNomeDeveFalhar() {
        assertThrows(NullPointerException.class, () -> {
            Empresa.builder(null, cnpj).responsavel(responsavel).build();
        });
    }

    @Test
    @DisplayName("Nome em brancou ou apenas espaços")
    void empresaComNomeEmBrancoDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> {
            Empresa.builder("", cnpj)
                .valorMercado(FastMoney.of(10000, "BRL"))
                .responsavel(responsavel)
            .build();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Empresa.builder("   ", cnpj)
                .valorMercado(FastMoney.of(10000, "BRL"))
                .responsavel(responsavel)
            .build();
        });
    }

    @Test
    @DisplayName("Sem cnpj")
    void empresaSemCnpjDeveFalhar() {
        assertThrows(NullPointerException.class, () -> {
            Empresa.builder(razaoSocial, null).responsavel(responsavel).build();
        });
    }

    @Test
    @DisplayName("Sem responsavel")
    void empresaSemResponsavelDeveFalhar() {
        assertThrows(NullPointerException.class, () -> {
            builder.responsavel(null).build();
        });
    }

    @Test
    @DisplayName("Sem funcionarios")
    void empresaSemEmpresagosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> builder.funcionarios(-10).build());
        assertThrows(IllegalArgumentException.class, () -> builder.funcionarios(0).build());
    }

    @Test
    @DisplayName("Sem valor de mercado")
    void empresaSemValorMercadoDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> builder.valorMercado(FastMoney.of(-10, "BRL")).build());
        assertThrows(IllegalArgumentException.class, () -> builder.valorMercado(FastMoney.of(0, "BRL")).build());
    }
}
