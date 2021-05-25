package com.nemesis.rio.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EnumUtilsKtTest {

    internal enum class TestEnum {
        ONE, HUNDRED
    }

    @Test
    fun searchEnumCaseInsensitive() {
        val enumName = "hundred"
        val result = searchEnumByName<TestEnum>(enumName)
        assertEquals(TestEnum.HUNDRED, result)
    }

    @Test
    fun searchEnumCaseSensitive() {
        val enumName = "one"
        val result: TestEnum? = searchEnumByName<TestEnum>(enumName, ignoreCase = false)
        assertNull(result)
    }
}
