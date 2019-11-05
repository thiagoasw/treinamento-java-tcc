package com.totvs.tjc.carteira;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static java.util.FormattableFlags.ALTERNATE;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.Formattable;
import java.util.Formatter;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
public final class Cnpj implements Formattable, Comparable<Cnpj> {

    private final String valor;

    public static Cnpj from(String value) {

        requireNonNull(value);

        String digitos = value.replaceAll("\\D", "");

        checkArgument(digitos.matches("\\d{14}"));
        checkArgument(!digitos.matches("(\\d)\\1+"));
        checkArgument(isValid(digitos.substring(0, 13)));
        checkArgument(isValid(digitos));

        return new Cnpj(digitos);
    }

    static boolean isValid(String digitos) {
        if (Long.parseLong(digitos) % 10 == 0) {
            return somaPonderada(digitos) % 11 < 2;
        } else {
            return somaPonderada(digitos) % 11 == 0;
        }
    }

    static int somaPonderada(String digitos) {
        char[] cs = digitos.toCharArray();
        int soma = 0;
        for (int i = 0; i < cs.length; i++) {
            int index = cs.length - i - 1;
            soma += Character.digit(cs[i], 10) * ((index % 9 + 1) + (index / 9));
        }
        return soma;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {

        StringBuilder sb = new StringBuilder();

        boolean alternate = (flags & ALTERNATE) == ALTERNATE;

        if (alternate) {

            sb.append(valor);
            
            while (width > sb.length()) {
                sb.insert(0, '0');
            }
        } else {
            sb.append(String.format("%s.%s.%s/%s-%s", valor.substring(0, 2), valor.substring(2, 5),
                    valor.substring(5, 8), valor.substring(8, 12), valor.substring(12)));
        }

        formatter.format(sb.toString());
    }

    @Override
    public int compareTo(Cnpj outro) {
        return valor.compareToIgnoreCase(outro.valor);
    }

}
