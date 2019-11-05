package com.totvs.tjc.carteira;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.spi.MoneyUtils;

public enum PoliticaLimiteAberturaConta {

    POLITICA_PADRAO((empresa, limiteMaximo) -> {
        MonetaryAmount relation = empresa.getValorMercado().divide(empresa.getFuncionarios());
        return relation.isGreaterThan(limiteMaximo) ? limiteMaximo : relation;
    }, FastMoney.of(15000, "BRL")),

    POLITICA_MEI((empresa, limiteMaximo) -> {
        MonetaryAmount relation = limiteMaximo.divideToIntegralValue(empresa.getFuncionarios());
        BigDecimal valorBase = MoneyUtils.getBigDecimal(relation.multiply(Math.random()).getNumber());
        return FastMoney.of(Math.floor(valorBase.doubleValue()), relation.getCurrency());
    }, FastMoney.of(5000, "BRL"));

    private final BiFunction<Empresa, MonetaryAmount, MonetaryAmount> fn;

    private final MonetaryAmount limiteMaximo;

    PoliticaLimiteAberturaConta(BiFunction<Empresa, MonetaryAmount, MonetaryAmount> fn, MonetaryAmount limiteMaximo) {
        this.fn = fn;
        this.limiteMaximo = limiteMaximo;
    }

    public MonetaryAmount getLimiteMaximo() {
        return limiteMaximo;
    }

    public MonetaryAmount calcularLimiteInicial(Empresa empresa) {
        return fn.apply(empresa, limiteMaximo);
    }

}
