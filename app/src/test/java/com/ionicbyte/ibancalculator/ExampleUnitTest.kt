package com.ionicbyte.ibancalculator

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun iban_is_correct() {
        val ccc = "00120345030000067890"
        val iban = IBAN.getIban("ES", ccc.substring(0, 4),
            ccc.substring(4, 8), ccc.substring(8, 10),
            ccc.substring(10, 20))

        assertEquals("ES07 0012 0345 03 0000067890", iban)
        print("Expected IBAN: ES07 0012 0345 03 0000067890, got $iban")
    }

    @Test
    fun ccc_validation_is_correct() {
        val ccc = "00120345030000067890"
        assertTrue(CCC.validateCCC(ccc))
    }
}