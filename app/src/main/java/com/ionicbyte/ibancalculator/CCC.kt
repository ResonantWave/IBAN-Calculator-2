package com.ionicbyte.ibancalculator

object CCC {
    fun validateCCC(ccc: String): Boolean {
        try {
            val bank = ccc.substring(0, 4)
            val office = ccc.substring(4, 8)
            val dc = ccc.substring(8, 10)
            val cccAccount = ccc.substring(10)
            val bankOffice = "00$bank$office"
            if (!obtainDC(bankOffice).equals(dc.substring(0, 1), ignoreCase = true)
                || !obtainDC(cccAccount).equals(
                    dc.substring(1, 2), ignoreCase = true
                )
            ) return false
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun obtainDC(value: String): String {
        val values = intArrayOf(1, 2, 4, 8, 5, 10, 9, 7, 3, 6)
        var control = 0
        for (i in 0..9) control += value[i].toString().toInt() * values[i]
        control = 11 - control % 11
        if (control == 11) control = 0 else if (control == 10) control = 1
        return control.toString()
    }
}