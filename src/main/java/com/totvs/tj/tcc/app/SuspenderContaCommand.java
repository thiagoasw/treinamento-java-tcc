package com.totvs.tj.tcc.app;

import com.totvs.tj.tcc.domain.conta.ContaId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "from")
public class SuspenderContaCommand {

    private ContaId conta;
    
}
