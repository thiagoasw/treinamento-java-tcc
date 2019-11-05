package com.totvs.tjc.infra.jpa.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Types;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.ContaId;

public class ContaIdTypeTest {

    private static IdCustomTypes.Conta type;

    @BeforeAll
    static void initAll() {
        type = new IdCustomTypes.Conta();
    }
    
    @Test
    void from() {
        ContaId id = ContaId.generate();
        assertEquals(id, type.from(id.toString()));
    }
    
    @Test
    void type() {
        assertEquals(ContaId.class, type.returnedClass());
    }
    
    @Test
    void sqlTypes() {
        assertEquals(1, type.sqlTypes().length);
        assertEquals(Types.VARCHAR, type.sqlTypes()[0]);
    }
    
    @Test
    void equalidade() {
        
        ContaId o1 = ContaId.generate();
        ContaId o2 = ContaId.generate();
            
        assertFalse(type.equals(o1, o2));
        assertTrue(type.equals(o1, o1));
    }
    
    @Test
    void hash() {
        ContaId id = ContaId.generate();
        assertEquals(Objects.hash(id), type.hashCode(id));
    }
    
    @Test
    void deepCopy() {
        ContaId id = ContaId.generate();
        assertEquals(id, type.deepCopy(id));
    }
    
    @Test
    void isImutavel() {
        assertFalse(type.isMutable());
    }
    
    @Test
    void disassemble() {
        ContaId id = ContaId.generate();
        assertEquals(id, type.disassemble(id));
    }
    
    @Test
    void assemble() {
        ContaId id = ContaId.generate();
        assertEquals(id, type.assemble(id, null));
    }
    
    @Test
    void replace() {
        ContaId id = ContaId.generate();
        assertEquals(id, type.replace(id, null, null));
    }
}
