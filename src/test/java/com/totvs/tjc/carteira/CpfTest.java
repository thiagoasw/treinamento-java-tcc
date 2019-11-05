package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.totvs.tjc.carteira.Cpf;

public class CpfTest {

    static Stream<String> testeValidados() {
        return Stream.of("185.302.491-00", "18530249100", "297.276.931-72", "29727693172", "046.428.359-03",
            "04642835903", "023.750.169-47", "02375016947", "855.826.525-90", "669.712.265-00", "01646227212",
            "96183390259", "63118670282", "57212970263", "85272175204", "84250739287", "74390651234",
            "93582803287", "84569190200", "51914794249", "67681530215", "51918102287", "59925272220",
            "72178507204", "85542520200", "98089242200", "66100313200", "51405300230", "13187110703");
    }

    static Stream<String> testeInvalidados() {
        return Stream.of("005.333.839-18", "00533383910", "030.405.039-35", "03040503934", "046.428.359-02",
            "04642835913", "023.750.169-57", "02375016937", "855.826.525-91", "669.712.265-10", "01646227211",
            "96183390269", "63118670283", "57212970273", "85272175206", "84250739284", "74390651233",
            "93582803297", "84569190201", "51914794259", "67681530214", "51918102282", "59925272221",
            "72178507294", "85542520210", "98089242210", "66100313201", "51405300238", "13187110704");
    }

    static Stream<String> testeRepetidos() {
        return Stream.of("00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
            "55555555555", "66666666666", "77777777777", "88888888888", "99999999999");
    }

    @MethodSource
    @ParameterizedTest
    void testeValidados(String s) {
        assertNotNull(Cpf.from(s));
    }

    @MethodSource
    @ParameterizedTest
    void testeInvalidados(String s) {
        assertThrows(IllegalArgumentException.class, () -> Cpf.from(s));
    }

    @MethodSource
    @ParameterizedTest
    void testeRepetidos(String s) {
        assertThrows(IllegalArgumentException.class, () -> Cpf.from(s));
    }

    @Test
    void testeCom10DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cpf.from("0123456789"));
    }

    @Test
    void testeCom12DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cpf.from("012345678901"));
    }

    @Test
    void testeCom15DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cpf.from("012345678901234"));
    }

    @Test
    void testeToString() {
        assertEquals(Cpf.from("18530249100").toString(), "18530249100");
        assertEquals(Cpf.from("29727693172").toString(), "29727693172");
    }

    @Test
    void testeFormatTo() {
        assertEquals(String.format("%s", Cpf.from("18530249100")), "185.302.491-00");
        assertEquals(String.format("%s", Cpf.from("29727693172")), "297.276.931-72");
    }

    @Test
    void testeFormatToAlternativo() {
        assertEquals(String.format("%#15s", Cpf.from("18530249100")), "000018530249100");
    }

    @Test
    void testeEquals() {
        assertEquals(Cpf.from("18530249100"), Cpf.from("18530249100"));
    }

    @Test
    void testeCompareTo() {
        assertTrue(Cpf.from("29727693172").compareTo(Cpf.from("18530249100")) > 0);
        assertTrue(Cpf.from("29727693172").compareTo(Cpf.from("29727693172")) == 0);
    }

}
