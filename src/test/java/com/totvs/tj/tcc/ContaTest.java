package com.totvs.tj.tcc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.totvs.tj.tcc.app.AbrirContaCommand;
import com.totvs.tj.tcc.app.ContaApplicationService;
import com.totvs.tj.tcc.domain.conta.Conta;
import com.totvs.tj.tcc.domain.conta.ContaId;
import com.totvs.tj.tcc.domain.conta.ContaRepository;
import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;

public class ContaTest {

    private final ContaId idConta = ContaId.generate();

    private final EmpresaId idEmpresa = EmpresaId.generate();

    private final ResponsavelId idResponsavel = ResponsavelId.generate();

    @Test
    public void aoCriarUmaConta() throws Exception {

        // WHEN
        Conta conta = Conta.builder()
                .id(idConta)
                .empresa(idEmpresa)
                .responsavel(idResponsavel)
                .build();

        // THEN
        assertNotNull(conta);

        assertEquals(idConta, conta.getId());
        assertEquals(idEmpresa, conta.getEmpresa());
        assertEquals(idResponsavel, conta.getResponsavel());

        assertEquals(idConta.toString(), conta.getId().toString());
        assertEquals(idEmpresa.toString(), conta.getEmpresa().toString());
        assertEquals(idResponsavel.toString(), conta.getResponsavel().toString());
    }

    @Test
    public void aoSolicitarAberturaConta() throws Exception {

        // GIVEN
        ContaRepository repository = new ContaRepositoryMock();
        ContaApplicationService service = new ContaApplicationService(repository);

        AbrirContaCommand cmd = AbrirContaCommand.builder()
                .empresa(idEmpresa)
                .responsavel(idResponsavel)
            .build();

        // WHEN
        ContaId idConta = service.handle(cmd);

        // THEN
        assertNotNull(idConta);
    }

    static class ContaRepositoryMock implements ContaRepository {
        @Override
        public void save(Conta conta) {
            System.out.println("Salvou a conta: " + conta);
        }
    }
}
