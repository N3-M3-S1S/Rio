package com.nemesis.rio.utils

import java.util.StringJoiner

inline fun joinStrings(
    delimiter: String = ",",
    prefix: String = "",
    suffix: String = "",
    block: StringJoiner.() -> Unit
): String = StringJoiner(delimiter, prefix, suffix).apply { block() }.toString()
