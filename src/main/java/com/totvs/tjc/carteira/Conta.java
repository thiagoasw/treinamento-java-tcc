package com.totvs.tjc.carteira;

import static com.totvs.tjc.carteira.PoliticaLimiteAberturaConta.POLITICA_PADRAO;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Columns;
import org.javamoney.moneta.Money;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PRIVATE, force = true)
@AllArgsConstructor(access = PRIVATE)

@Entity
public final class Conta {

    public static final MonetaryAmount SALDO_INICIAL = Money.of(0, "BRL");

    @Id
    private final ContaId id;

    @Embedded
    private final Empresa empresa;

    @Columns(columns = { @Column(name = "limite_moeda"), @Column(name = "limite") })
    private MonetaryAmount limite;

    @Columns(columns = { @Column(name = "saldo_moeda"), @Column(name = "saldo") })
    private MonetaryAmount saldoDevedor;

    public static Conta from(Empresa empresa) {

        requireNonNull(empresa);

        ContaId id = ContaId.generate();
        MonetaryAmount limite = POLITICA_PADRAO.calcularLimiteInicial(empresa);

        return new Conta(id, empresa, limite, SALDO_INICIAL);
    }

    public boolean hasLimiteDisponivel(MonetaryAmount valor) {
        return limite.subtract(saldoDevedor).isGreaterThanOrEqualTo(valor);
    }

    public void creditar(MonetaryAmount valor) {
        saldoDevedor = saldoDevedor.add(valor);
    }
    
    public void debitar(MonetaryAmount valor) {
        saldoDevedor = saldoDevedor.subtract(valor);
    }
    
}
