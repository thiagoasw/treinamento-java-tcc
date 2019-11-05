package com.totvs.tjc.app.emprestimo;

import static com.totvs.tjc.carteira.Conta.SALDO_INICIAL;
import static com.totvs.tjc.emprestimo.Emprestimo.Situacao.LIBERADO;
import static com.totvs.tjc.emprestimo.Emprestimo.Situacao.NEGADO;
import static com.totvs.tjc.emprestimo.Emprestimo.Situacao.QUITADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.money.MonetaryAmount;
import javax.validation.Validation;
import javax.validation.Validator;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.app.EmprestimoApplicationService;
import com.totvs.tjc.app.EmprestimoCommands.AprovarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.QuitarEmprestimo;
import com.totvs.tjc.app.EmprestimoCommands.RecusarEmprestimoPendente;
import com.totvs.tjc.app.EmprestimoCommands.SolicitarEmprestimo;
import com.totvs.tjc.app.carteira.CarteiraDomainRepositoryMock;
import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.Conta;
import com.totvs.tjc.carteira.Cpf;
import com.totvs.tjc.carteira.Empresa;
import com.totvs.tjc.carteira.Responsavel;
import com.totvs.tjc.emprestimo.Emprestimo;
import com.totvs.tjc.emprestimo.EmprestimoId;

public class SolicitarEmprestimoTest {

    private static EmprestimoApplicationService service;

    private static EmprestimoDomainRepositoryMock repository;

    private static CarteiraDomainRepositoryMock repositoryCarteira;

    private static Validator validator;

    private static Cnpj cnpj;

    private static Conta conta;

    @BeforeAll
    static void initAll() {
        cnpj = Cnpj.from("19.861.350/0001-70");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void init() {

        Empresa empresa = Empresa.builder("Valasso SA", cnpj)
            .responsavel(Responsavel.of("Rosevaldo Braga", Cpf.from("004.574.359-25")))
            .funcionarios(50)
            .valorMercado(Money.of(500000, "BRL"))
            .build();
        
        conta = Conta.from(empresa);
        
        repositoryCarteira = new CarteiraDomainRepositoryMock();
        repositoryCarteira.save(conta);

        repository = new EmprestimoDomainRepositoryMock();
        service = new EmprestimoApplicationService(repository, repositoryCarteira);
    }

    @Test
    @DisplayName("Comando de solicitar sem cnpj")
    void comandoSolicitarEmprestimoSemCnpj() {
        SolicitarEmprestimo cmd = SolicitarEmprestimo.of(null, Money.of(10, "BRL"));
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Comando de solicitar emprestimo negativo")
    void comandoSolicitarEmprestimoNegativo() {
        SolicitarEmprestimo cmd = SolicitarEmprestimo.of(cnpj, Money.of(10, "BRL").negate());
        assertFalse(validator.validate(cmd).isEmpty());
    }

    @Test
    @DisplayName("Solicitar emprestimo sem ter uma conta")
    void solicitarEmprestimoSemConta() {

        SolicitarEmprestimo cmd = SolicitarEmprestimo.of(Cnpj.from("24.152.237/0001-56"), Money.of(10, "BRL"));

        assertTrue(validator.validate(cmd).isEmpty());
        assertNull(service.handle(cmd));
    }

    @Test
    @DisplayName("Caminho feliz: solicitar")
    void solicitarEmprestimo() {

        SolicitarEmprestimo cmd = SolicitarEmprestimo.of(cnpj, Money.of(10, "BRL"));

        assertTrue(validator.validate(cmd).isEmpty());
        assertNotNull(service.handle(cmd));
    }

    @Test
    @DisplayName("Caminho feliz: aprovar")
    void aprovarEmprestimo() {

        MonetaryAmount valor = Money.of(10, "BRL");
        
        EmprestimoId id = service.handle(SolicitarEmprestimo.of(cnpj, valor));

        AprovarEmprestimoPendente cmd = AprovarEmprestimoPendente.from(id);

        assertTrue(validator.validate(cmd).isEmpty());

        service.handle(cmd);

        Emprestimo emprestimo = repository.getOne(id);

        assertNotNull(emprestimo);
        assertEquals(LIBERADO, emprestimo.getSituacao());
        assertEquals(valor, emprestimo.getConta().getSaldoDevedor());
    }

    @Test
    @DisplayName("Aprovar emprestimo para emprestimo inexistente")
    void aprovarEmprestimoInexistente() {

        EmprestimoId id = EmprestimoId.generate();

        service.handle(AprovarEmprestimoPendente.from(id));
     
        // FIXME: Lançar as exceções corretamente...
        assertEquals(null, repository.getOne(id));
    }
    
    @Test
    @DisplayName("Caminho feliz: recusar")
    void recusarEmprestimo() {

        MonetaryAmount valor = Money.of(10, "BRL");

        EmprestimoId id = service.handle(SolicitarEmprestimo.of(cnpj, valor));

        String motivo = "recusado";
        RecusarEmprestimoPendente cmd = RecusarEmprestimoPendente.of(id, motivo);

        assertTrue(validator.validate(cmd).isEmpty());

        service.handle(cmd);

        Emprestimo emprestimo = repository.getOne(id);

        assertNotNull(emprestimo);
        assertEquals(NEGADO, emprestimo.getSituacao());
        assertEquals(motivo, emprestimo.getObservacao());
        assertEquals(SALDO_INICIAL, emprestimo.getConta().getSaldoDevedor());
    }
    
    @Test
    @DisplayName("Recusar emprestimo para emprestimo inexistente")
    void recusarEmprestimoInexistente() {

        EmprestimoId id = EmprestimoId.generate();

        service.handle(RecusarEmprestimoPendente.of(id, "recusado"));
     
        // FIXME: Lançar as exceções corretamente...
        assertEquals(null, repository.getOne(id));
    }
    
    @Test
    @DisplayName("Caminho feliz: quitar")
    void quitarEmprestimo() {

        MonetaryAmount valor = Money.of(10, "BRL");
        
        EmprestimoId id = service.handle(SolicitarEmprestimo.of(cnpj, valor));
        service.handle(AprovarEmprestimoPendente.from(id));

        QuitarEmprestimo cmd = QuitarEmprestimo.from(id);
        
        assertTrue(validator.validate(cmd).isEmpty());

        service.handle(cmd);

        Emprestimo emprestimo = repository.getOne(id);

        assertNotNull(emprestimo);
        assertEquals(QUITADO, emprestimo.getSituacao());
        assertEquals(SALDO_INICIAL, emprestimo.getConta().getSaldoDevedor());
    }
    
    @Test
    @DisplayName("Quitar emprestimo para emprestimo inexistente")
    void quitarEmprestimoInexistente() {

        EmprestimoId id = EmprestimoId.generate();
        
        service.handle(QuitarEmprestimo.from(id));
     
        // FIXME: Lançar as exceções corretamente...
        assertEquals(null, repository.getOne(id));
    }

}
