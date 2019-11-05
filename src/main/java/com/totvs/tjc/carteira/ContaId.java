package com.totvs.tjc.carteira;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static com.totvs.tjc.util.UUIDUtils.isUUID;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ContaId implements Serializable {

    private static final long serialVersionUID = 3745422523985479303L;

    private final String valor;

    public static ContaId from(String codigo) {
        checkArgument(isUUID(codigo));
        return new ContaId(codigo);
    }

    public static ContaId generate() {
        return new ContaId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return valor;
    }

}
