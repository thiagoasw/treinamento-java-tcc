package com.totvs.tjc.infra.jpa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.totvs.tjc.carteira.Cnpj;

@DisplayName("Teste de convers\u00E3o de cnpj (jpa)")
public class CnpjConvertTest {

    private static CnpjConverter converter;

    private static String cnpjValido;
    
    private static String cnpjInvalido;
    
    @BeforeAll
    static void initAll() {
        converter = new CnpjConverter();
        cnpjValido = "19861350000170";
        cnpjInvalido = "06305901000177";
    }
    
    @Test
    void doCnpjParaBanco() {
        assertEquals(cnpjValido, converter.convertToDatabaseColumn(Cnpj.from(cnpjValido)));
        assertThrows(NullPointerException.class, () -> converter.convertToDatabaseColumn(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToDatabaseColumn(Cnpj.from(cnpjInvalido)));
    }
    
    @Test
    void doBancoParaCnpj() {
        assertEquals(Cnpj.from(cnpjValido), converter.convertToEntityAttribute(cnpjValido));
        assertThrows(NullPointerException.class, () -> converter.convertToEntityAttribute(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(cnpjInvalido));
    }
    
}
