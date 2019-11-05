package com.totvs.tjc.infra.jpa.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.Objects;

import javax.money.MonetaryAmount;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MonetaryTypeTest {

    private static MonetaryAmountType type;

    @BeforeAll
    static void initAll() {
        type = new MonetaryAmountType();
    }
    
    @Test
    void type() {
        assertEquals(FastMoney.class, type.returnedClass());
    }

    @Test
    void propertyNames() {
        assertEquals(2, type.getPropertyNames().length);
        assertEquals("currency", type.getPropertyNames()[0]);
        assertEquals("amount", type.getPropertyNames()[1]);
    }
    
    @Test
    void propertyTypes() {
        assertEquals(2, type.getPropertyTypes().length);
        assertEquals(StringType.INSTANCE, type.getPropertyTypes()[0]);
        assertEquals(BigDecimalType.INSTANCE, type.getPropertyTypes()[1]);
    }
    
    @Test
    void equalidade() {
        
        MonetaryAmount o1 = FastMoney.of(10, "BRL");
        MonetaryAmount o2 = FastMoney.of(10, "USD");
            
        assertFalse(type.equals(o1, o2));
        assertTrue(type.equals(o1, o1));
    }
    
    @Test
    void hash() {
        MonetaryAmount value = FastMoney.of(10, "BRL");
        assertEquals(Objects.hash(value), type.hashCode(value));
    }
    
    @Test
    void deepCopy() {
        MonetaryAmount value = FastMoney.of(10, "BRL");
        assertEquals(value, type.deepCopy(value));
    }
    
    @Test
    void isImutavel() {
        assertFalse(type.isMutable());
    }
    
    @Test
    void disassemble() {
        MonetaryAmount value = FastMoney.of(10, "BRL");
        assertEquals(value, type.disassemble(value, null));
    }
    
    @Test
    void assemble() {
        MonetaryAmount value = FastMoney.of(10, "BRL");
        assertEquals(value, type.assemble((Serializable) value, null, null));
    }
    
    @Test
    void replace() {
        MonetaryAmount value = FastMoney.of(10, "BRL");
        assertEquals(value, type.replace(value, null, null, null));
    }
}
