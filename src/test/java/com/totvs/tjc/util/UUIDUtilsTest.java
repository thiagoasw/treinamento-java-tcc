package com.totvs.tjc.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UUIDUtilsTest {

    @Test
    void uuidValido() {
        assertTrue(UUIDUtils.isUUID("66e47b82-4ce6-4352-ad09-a3ff0a2f11e4"));
    }
    
    @Test
    void uuidInvalido() {
        assertFalse(UUIDUtils.isUUID("66e47b8204ce6043520ad090a3ff0a2f11e4"));
    }

    @Test
    void uuidComDash4Invalido() {
        assertFalse(UUIDUtils.isUUID("66e47b82-4ce6-4352aad09aa3ff0a2f11e4"));
    }
    
    @Test
    void uuidComDash5Invalido() {
        assertFalse(UUIDUtils.isUUID("66e47b82-4ce6-4352-ad09-a3ff0-2f11e4"));
    }
    
    @Test
    void uuidCom40Digitos() {
        assertFalse(UUIDUtils.isUUID("66e47b82-4ce6-4352-ad09-a3ff0a2f11e40000"));
    }
    
}
