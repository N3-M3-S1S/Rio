package com.nemesis.rio.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilsKtTest {

    @Test
    fun `join strings`() {
        val expectedString = "a-b-c"
        val resultString = joinStrings(delimiter = "-") {
            add("a")
            add("b")
            add("c")
        }
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `join strings with prefix`() {
        val expectedString = "=a,b,c"
        val resultString = joinStrings(prefix = "=") {
            add("a")
            add("b")
            add("c")
        }
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `join strings with suffix`() {
        val expectedString = "a,b,c="
        val resultString = joinStrings(suffix = "=") {
            add("a")
            add("b")
            add("c")
        }
        assertEquals(expectedString, resultString)
    }

    @Test
    fun `join strings with prefix and suffix`() {
        val expectedString = "+a,b,c="
        val resultString = joinStrings(prefix = "+", suffix = "=") {
            add("a")
            add("b")
            add("c")
        }
        assertEquals(expectedString, resultString)
    }
}
