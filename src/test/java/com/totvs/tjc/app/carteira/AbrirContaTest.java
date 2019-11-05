package com.totvs.tjc.app.carteira;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.app.CarteiraApplicationService;
import com.totvs.tjc.app.CarteiraCommands.AbrirConta;
import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.Cpf;
import com.totvs.tjc.carteira.Responsavel;

public class AbrirContaTest {

    private static CarteiraApplicationService service;

    private static AbrirConta.AbrirContaBuilder builder;

    private static Validator validator;

    @BeforeAll
    static void initAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void init() {
        
        service = new CarteiraApplicationService(new CarteiraDomainRepositoryMock());
        
        builder = AbrirConta.builder()
            .name("Algora LTDA")
            .cnpj(Cnpj.from("19.861.350/0001-70"))
            .funcionarios(30)
            .valorMercado(Money.of(30000, "BRL"))
            .responsavel(Responsavel.of("Lucio", Cpf.from("185.302.491-00")));
    }

    @Test
    @DisplayName("Comando de abrir conta sem funcionarios")
    void comandoAbrirContaSemFuncionarios() {
        AbrirConta cmd = builder.funcionarios(0).build();
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Comando de abrir conta sem valor de mercado")
    void comandoAbrirContaSemValorMercado() {
        AbrirConta cmd = builder.valorMercado(Money.of(0, "BRL")).build();
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Comando de abrir conta sem nome")
    void comandoAbrirContaSemNome() {
        AbrirConta cmd = builder.name(null).build();
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Comando de abrir conta com nome em branco")
    void comandoAbrirContaComNomeEmBranco() {
        AbrirConta cmd = builder.name("    ").build();
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Sem comando")
    void abrirContaSemComandoNpe() {
        assertThrows(NullPointerException.class, () -> service.handle(null));
    }

    @Test
    @DisplayName("Caminho feliz")
    void abrirConta() {

        AbrirConta cmd = builder.build();

        assertTrue(validator.validate(cmd).isEmpty());
        assertNotNull(service.handle(cmd));
    }

    @Test
    @DisplayName("Abertura de uma conta para uma mesma empresa")
    void abrirContaMaisDeUmaContaParaMesmaEmpresa() {

        AbrirConta cmd = builder.build();

        assertNotNull(service.handle(cmd));
        assertThrows(IllegalStateException.class, () -> service.handle(cmd));
    }

}
