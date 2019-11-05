package com.totvs.tjc.infra.jpa.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Types;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.emprestimo.EmprestimoId;

public class EmprestimoIdTypeTest {

    private static IdCustomTypes.Emprestimo type;

    @BeforeAll
    static void initAll() {
        type = new IdCustomTypes.Emprestimo();
    }
    
    @Test
    void from() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, type.from(id.toString()));
    }

    @Test
    void type() {
        assertEquals(EmprestimoId.class, type.returnedClass());
    }
    
    @Test
    void sqlTypes() {
        assertEquals(1, type.sqlTypes().length);
        assertEquals(Types.VARCHAR, type.sqlTypes()[0]);
    }
    
    @Test
    void equalidade() {
        
        EmprestimoId o1 = EmprestimoId.generate();
        EmprestimoId o2 = EmprestimoId.generate();
            
        assertFalse(type.equals(o1, o2));
        assertTrue(type.equals(o1, o1));
    }
    
    @Test
    void hash() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(Objects.hash(id), type.hashCode(id));
    }
    
    @Test
    void deepCopy() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, type.deepCopy(id));
    }
    
    @Test
    void isImutavel() {
        assertFalse(type.isMutable());
    }
    
    @Test
    void disassemble() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, type.disassemble(id));
    }
    
    @Test
    void assemble() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, type.assemble(id, null));
    }
    
    @Test
    void replace() {
        EmprestimoId id = EmprestimoId.generate();
        assertEquals(id, type.replace(id, null, null));
    }
}
