package com.totvs.tjc.app;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.Responsavel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public interface CarteiraCommands {

    @Data
    @Builder
    public static final class AbrirConta {

        @NotBlank
        @ApiModelProperty(required = true)
        private final String name;

        @NotNull
        @ApiModelProperty(required = true)
        private final Cnpj cnpj;

        @Positive
        @ApiModelProperty(required = true)
        private final int funcionarios;

        @Positive
        @ApiModelProperty(required = true)
        private final MonetaryAmount valorMercado;

        @NotNull
        @ApiModelProperty(required = true)
        private final Responsavel responsavel;

    }

}
