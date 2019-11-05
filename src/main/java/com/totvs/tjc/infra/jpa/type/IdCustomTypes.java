package com.totvs.tjc.infra.jpa.type;

import com.totvs.tjc.carteira.ContaId;
import com.totvs.tjc.emprestimo.EmprestimoId;

public interface IdCustomTypes {

    public final static class Conta extends IdCustomType<ContaId> {

        @Override
        public Class<ContaId> returnedClass() {
            return ContaId.class;
        }

        @Override
        public ContaId from(String value) {
            return ContaId.from(value);
        }
    }

    public final static class Emprestimo extends IdCustomType<EmprestimoId> {

        @Override
        public Class<EmprestimoId> returnedClass() {
            return EmprestimoId.class;
        }

        @Override
        public EmprestimoId from(String value) {
            return EmprestimoId.from(value);
        }
    }

}
