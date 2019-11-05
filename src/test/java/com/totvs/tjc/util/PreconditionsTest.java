package com.totvs.tjc.util;

import static com.totvs.tjc.util.Preconditions.checkArgument;
import static com.totvs.tjc.util.Preconditions.checkState;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PreconditionsTest {

    @Test
    void argumentoIllegalSemMensagem() {
        assertThrows(IllegalArgumentException.class, () -> checkArgument(false));
    }

    @Test
    void argumentoIllegalComMensagem() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> checkArgument(false, "msg"));
        assertEquals("msg", ex.getMessage());
    }

    @Test
    void argumentoLegalSemMensagem() {
        checkArgument(true);
        assertTrue(true);
    }

    @Test
    void argumentoLegalComMensagem() {
        checkArgument(true, "msg");
        assertTrue(true);
    }

    @Test
    void estadoIllegalSemMensagem() {
        assertThrows(IllegalStateException.class, () -> checkState(false));
    }

    @Test
    void estadoIllegalComMensagem() {
        Exception ex = assertThrows(IllegalStateException.class, () -> checkState(false, "msg"));
        assertEquals("msg", ex.getMessage());
    }

    @Test
    void estadoLegalSemMensagem() {
        checkState(true);
        assertTrue(true);
    }

    @Test
    void estadoLegalComMensagem() {
        checkState(true, "msg");
        assertTrue(true);
    }
}
