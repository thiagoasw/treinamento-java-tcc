package com.totvs.tjc.emprestimo;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static com.totvs.tjc.util.UUIDUtils.isUUID;

import java.io.Serializable;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmprestimoId implements Serializable {

    private static final long serialVersionUID = 7462761319667662054L;
    
    private final String valor;

    public static EmprestimoId from(String codigo) {
        checkArgument(isUUID(codigo));
        return new EmprestimoId(codigo);
    }

    public static EmprestimoId generate() {
        return new EmprestimoId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return valor;
    }

}
