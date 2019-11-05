package com.totvs.tjc.app;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.emprestimo.EmprestimoId;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public interface EmprestimoCommands {

    @Data(staticConstructor = "of")
    public static final class SolicitarEmprestimo {

        @NotNull
        @ApiModelProperty(required = true)
        private final Cnpj cnpj;
        
        @Positive
        @ApiModelProperty(required = true)
        private final MonetaryAmount valor;
        
    }
    
    @Data(staticConstructor = "from")
    public static final class AprovarEmprestimoPendente {
        
        @NotNull
        @ApiModelProperty(required = true)
        private final EmprestimoId id;
        
    }
    
    @Data(staticConstructor = "of")
    public static final class RecusarEmprestimoPendente {
        
        @NotNull
        @ApiModelProperty(required = true)
        private final EmprestimoId id;
        
        @NotBlank
        private final String motivo;
        
    }
    
    @Data(staticConstructor = "from")
    public static final class QuitarEmprestimo {
        
        @NotNull
        @ApiModelProperty(required = true)
        private final EmprestimoId id;
        
    }
    
}
