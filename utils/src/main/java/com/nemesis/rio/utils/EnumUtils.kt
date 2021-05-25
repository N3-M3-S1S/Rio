package com.nemesis.rio.utils

import java.util.EnumMap

inline fun <reified K : Enum<K>, V> enumMap() = EnumMap<K, V>(K::class.java)

inline fun <reified T : Enum<T>> searchEnumByName(name: String, ignoreCase: Boolean = true): T? =
    enumValues<T>().find { it.name.equals(name, ignoreCase) }
