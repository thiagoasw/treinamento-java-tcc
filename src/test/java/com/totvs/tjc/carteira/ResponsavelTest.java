package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Teste de responsavel")
public class ResponsavelTest {

    private static String nome;

    private static Cpf documento;

    @BeforeAll
    static void initAll() {
        documento = Cpf.from("004.574.359-25");
        nome = "Josualdo Neves";
    }

    @Test
    @DisplayName("Caminho feliz")
    void construirResponsavelDeveFuncionar() {

        Responsavel responsavel = Responsavel.of(nome, documento);

        assertEquals(nome, responsavel.getNome());
        assertEquals(documento, responsavel.getCpf());
    }

    @Test
    @DisplayName("Sem nome")
    void responsavelSemNomeDeveFalhar() {
        assertThrows(NullPointerException.class, () -> Responsavel.of(null, documento));
    }

    @Test
    @DisplayName("Nome em brancou ou apenas espaços")
    void responsavelComNomeEmBrancoDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Responsavel.of("", documento));
        assertThrows(IllegalArgumentException.class, () -> Responsavel.of("  ", documento));
    }

    @Test
    @DisplayName("Sem cpf")
    void responsavelSemCpfDeveFalhar() {
        assertThrows(NullPointerException.class, () -> Responsavel.of(null, documento));
    }

}
