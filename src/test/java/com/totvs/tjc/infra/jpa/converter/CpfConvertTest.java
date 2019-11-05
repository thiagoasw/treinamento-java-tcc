package com.totvs.tjc.infra.jpa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.Cpf;

@DisplayName("Teste de convers\u00E3o de cpf (jpa)")
public class CpfConvertTest {

    private static CpfConverter converter;

    private static String cpfValido;
    
    private static String cpfInvalido;
    
    @BeforeAll
    static void initAll() {
        converter = new CpfConverter();
        cpfValido = "04642835903";
        cpfInvalido = "04642835913";
    }
    
    @Test
    void doCpfParaBanco() {
        assertEquals(cpfValido, converter.convertToDatabaseColumn(Cpf.from(cpfValido)));
        assertThrows(NullPointerException.class, () -> converter.convertToDatabaseColumn(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToDatabaseColumn(Cpf.from(cpfInvalido)));
    }
    
    @Test
    void doBancoParaCpf() {
        assertEquals(Cpf.from(cpfValido), converter.convertToEntityAttribute(cpfValido));
        assertThrows(NullPointerException.class, () -> converter.convertToEntityAttribute(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(cpfInvalido));
    }
    
}
