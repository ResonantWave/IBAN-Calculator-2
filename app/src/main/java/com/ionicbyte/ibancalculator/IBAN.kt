package com.ionicbyte.ibancalculator

import java.math.BigInteger

object IBAN {
    fun getIban(
        countryCode: String, entity: String, office: String,
        dc: String, accountNumber: String
    ): String {
        val dcIban = ccIban(countryCode, entity, office, dc, accountNumber)
        return "${countryCode}${dcIban} $entity $office $dc $accountNumber"
    }

    private fun ccIban(
        countryCode: String, entity: String, office: String,
        dc: String, accountNumber: String
    ): String {
        val preIban = (entity + office + dc + accountNumber
                + valueIBAN(countryCode[0])
                + valueIBAN(countryCode[1]) + "00")
        var ccc = BigInteger(preIban)
        ccc = ccc.mod(BigInteger("97"))
        var dcIb: Int = ccc.toInt()
        dcIb = 98 - dcIb
        return leftZero(dcIb.toString(), 2)
    }

    private fun leftZero(string: String, lon: Int): String {
        var str = string
        var zero = ""
        if (str.length < lon) {
            for (i in 0 until lon - str.length) {
                zero += '0'
            }
            str = zero + str
        }
        return str
    }

    // this method returns the weight of each corresponding alphabet letter.
    // letter A has a weight of 10, and it increments until 35, Z.
    // to return this weight, we get the ASCII code of the letter and subtract 55.
    // for example, A is 65 in ASCII, 65 - 55 = 10.
    // another example, Z is 90 in ASCII, 90 - 55 = 35.
    private fun valueIBAN(letter: Char): String {
        return (letter.toUpperCase().toByte().toInt() - 55).toString()
    }
}