package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.EmpresaId;
import com.totvs.tj.tcc.domain.conta.ResponsavelId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbrirContaCommand {

    private EmpresaId empresa;
    
    private ResponsavelId responsavel;
    
}
