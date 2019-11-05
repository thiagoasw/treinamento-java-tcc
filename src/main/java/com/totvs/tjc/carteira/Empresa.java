package com.totvs.tjc.carteira;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.StringUtils.hasText;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Embedded;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "cnpj")
@NoArgsConstructor(access = PRIVATE, force = true)
@AllArgsConstructor(access = PRIVATE)
public final class Empresa {

    private final String razaoSocial;

    @NaturalId
    private final Cnpj cnpj;

    private final int funcionarios;

    @Columns(columns = { @Column(name = "valor_mercado_moeda"), @Column(name = "valor_mercado") })
    private final MonetaryAmount valorMercado;

    @Embedded
    private final Responsavel responsavel;

    public static Builder builder(String nome, Cnpj documento) {
        return new Builder(nome, documento);
    }

    public static class Builder {

        private final String razaoSocial;

        private final Cnpj cnpj;

        private int funcionarios;

        private MonetaryAmount valorMercado;

        private Responsavel responsavel;

        public Builder(String razaoSocial, Cnpj cnpj) {
            this.razaoSocial = razaoSocial;
            this.cnpj = cnpj;
        }

        public Builder responsavel(Responsavel responsavel) {
            this.responsavel = responsavel;
            return this;
        }

        public Builder funcionarios(int funcionarios) {
            this.funcionarios = funcionarios;
            return this;
        }

        public Builder valorMercado(MonetaryAmount valorMercado) {
            this.valorMercado = valorMercado;
            return this;
        }

        public Empresa build() {
            return new Empresa(this);
        }
    }

    private Empresa(Builder builder) {

        razaoSocial = requireNonNull(builder.razaoSocial);
        cnpj = requireNonNull(builder.cnpj);
        responsavel = requireNonNull(builder.responsavel);
        funcionarios = builder.funcionarios;
        valorMercado = requireNonNull(builder.valorMercado);

        checkArgument(hasText(razaoSocial));
        checkArgument(funcionarios > 0);
        checkArgument(valorMercado.isPositive());
    }

}