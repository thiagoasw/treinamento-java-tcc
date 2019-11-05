package com.totvs.tjc.emprestimo;

import static java.util.Objects.requireNonNull;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Columns;

import com.totvs.tjc.carteira.Conta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PRIVATE, force = true)
@AllArgsConstructor(access = PRIVATE)

@Entity
public final class Emprestimo implements SituacaoEmprestimo.Solicitado, SituacaoEmprestimo.Pendente, SituacaoEmprestimo.Liberado {

    @Id
    private EmprestimoId id;

    @OneToOne(fetch = LAZY, optional = false)
    private Conta conta;

    @Columns(columns = { @Column(name = "moeda"), @Column(name = "valor") })
    private MonetaryAmount valor;

    @Enumerated(STRING)
    private Situacao situacao;

    private String observacao;

    private LocalDateTime solicitadoEm;

    private LocalDateTime ultimaMovimentacaoEm;

    private LocalDateTime liberadoEm;

    private LocalDateTime quitadoEm;

    public static SituacaoEmprestimo.Solicitado of(Conta conta, MonetaryAmount valor) {

        requireNonNull(conta);
        requireNonNull(valor);

        EmprestimoId id = EmprestimoId.generate();

        Emprestimo emprestimo = Emprestimo.builder()
            .id(id)
            .conta(conta)
            .valor(valor)
            .solicitadoEm(LocalDateTime.now())
            .ultimaMovimentacaoEm(LocalDateTime.now())
            .situacao(Situacao.PENDENTE)
            .build();

        if (!conta.hasLimiteDisponivel(valor))
            emprestimo.recusar("Sem limite dispon\u00EDvel.");

        return emprestimo;
    }

    @Override
    public void aceitar() {
        ultimaMovimentacaoEm = LocalDateTime.now();
        situacao = situacao.next();
        liberadoEm = LocalDateTime.now();
        conta.creditar(valor);
    }

    @Override
    public void recusar(String motivo) {
        ultimaMovimentacaoEm = LocalDateTime.now();
        observacao = motivo;
        situacao = situacao.deny();
    }

    @Override
    public void quitar() {
        ultimaMovimentacaoEm = LocalDateTime.now();
        situacao = situacao.next();
        quitadoEm = LocalDateTime.now();
        conta.debitar(valor);
    }

    public enum Situacao {

        PENDENTE {
            @Override
            public Situacao next() {
                return LIBERADO;
            }
        },
        LIBERADO {
            @Override
            public Situacao next() {
                return QUITADO;
            }
        },
        QUITADO {
            @Override
            public Situacao deny() {
                return this;
            }
        },
        NEGADO;

        public Situacao next() {
            return this;
        }

        public Situacao deny() {
            return NEGADO;
        }
    }
}
