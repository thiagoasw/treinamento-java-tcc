package com.totvs.tj.tcc.domain.conta;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Conta {

    private ContaId id;
    
    private EmpresaId empresa;
    
    private ResponsavelId responsavel;

}
