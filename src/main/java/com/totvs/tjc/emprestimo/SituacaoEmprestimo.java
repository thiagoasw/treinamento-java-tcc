package com.totvs.tjc.emprestimo;

public interface SituacaoEmprestimo {

    public static interface Solicitado {
        
        EmprestimoId getId();

    }
    
    public static interface Pendente extends Solicitado {

        void aceitar();

        void recusar(String motivo);

    }

    public static interface Liberado extends Solicitado {

        void quitar();

    }

}
