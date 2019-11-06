package com.totvs.tjc.carteira;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.StringUtils.hasText;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "cpf")
@NoArgsConstructor(access = PRIVATE, force = true)
@AllArgsConstructor(access = PRIVATE)
public final class Responsavel {

    private final String nome;

    private final Cpf cpf;

    public static Responsavel of(String nome, Cpf cpf) {
        checkArgument(hasText(requireNonNull(nome)));
        return new Responsavel(nome, requireNonNull(cpf));
    }

}
