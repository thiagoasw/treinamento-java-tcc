package com.totvs.tjc.carteira;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.totvs.tjc.carteira.Cnpj;

public class CnpjTest {

    static Stream<String> testeValidados() {
        return Stream.of("19.861.350/0001-70", "19861350000170", "23.144.170/0001-45", "23144170000145",
            "46.868.328/0001-25", "42.767.194/0001-03", "58.647.246/0001-30", "37.961.612/0001-50",
            "43.256.675/0001-09", "24.152.237/0001-56", "60.871.888/0001-60", "66.845.982/0001-20",
            "06.074.614/0001-02", "06074614000102", "86.222.998/0001-94", "74.345.224/0001-71",
            "13.544.868/0001-02", "23.325.412/0001-05", "21.319.627/0001-80", "36.747.518/0001-30");
    }

    static Stream<String> testeInvalidados() {
        return Stream.of("06.305.901/0001-77", "06305901000179", "23.144.170/0001-35", "23144170000155",
            "46.868.328/0001-26", "42.767.194/0001-13", "58.647.246/0001-31", "37.961.612/0001-51",
            "43.256.675/0001-08", "24.152.237/0001-57", "60.871.888/0001-61", "66.845.982/0001-21",
            "06.074.614/0001-12", "06074614000103", "86.222.998/0001-93", "74.345.224/0001-72",
            "13.544.868/0001-03", "23.325.412/0001-02", "21.319.627/0001-82", "36.747.518/0001-41", "10100000000000");
    }

    static Stream<String> testeRepetidos() {
        return Stream.of("00000000000000", "11111111111111", "22222222222222", "33333333333333",
            "44444444444444", "55555555555555", "66666666666666", "77777777777777",
            "88888888888888", "99999999999999");
    }

    @MethodSource
    @ParameterizedTest
    void testeValidados(String s) {
        assertNotNull(Cnpj.from(s));
    }

    @MethodSource
    @ParameterizedTest
    void testeInvalidados(String s) {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.from(s));
    }

    @MethodSource
    @ParameterizedTest
    void testeRepetidos(String s) {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.from(s));
    }

    @Test
    void testeCom10DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.from("0123456789"));
    }

    @Test
    void testeCom12DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.from("012345678901"));
    }

    @Test
    void testeCom15DigitosDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.from("012345678901234"));
    }

    @Test
    void testeToString() {
        assertEquals(Cnpj.from("19861350000170").toString(), "19861350000170");
    }

    @Test
    void testeFormatTo() {
        assertEquals(String.format("%s", Cnpj.from("19861350000170")), "19.861.350/0001-70");
    }

    @Test
    void testeFormatToAlternativo() {
        assertEquals(String.format("%#15s", Cnpj.from("19861350000170")), "019861350000170");
    }

    @Test
    void testeEquals() {
        assertEquals(Cnpj.from("19.861.350/0001-70"), Cnpj.from("19861350000170"));
    }

    @Test
    void testeCompareTo() {
        assertTrue(Cnpj.from("23144170000145").compareTo(Cnpj.from("19861350000170")) > 0);
        assertTrue(Cnpj.from("19861350000170").compareTo(Cnpj.from("19861350000170")) == 0);
    }

}
