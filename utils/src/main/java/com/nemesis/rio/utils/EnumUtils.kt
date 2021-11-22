package com.nemesis.rio.utils

import java.util.*

inline fun <reified K : Enum<K>, V> enumMap() = EnumMap<K, V>(K::class.java)

inline fun <reified K : Enum<K>, V> enumMapOf(vararg pairs: Pair<K, V>): EnumMap<K, V> {
    val map = enumMap<K, V>()
    for (pair in pairs) {
        map[pair.first] = pair.second
    }
    return map
}

inline fun <reified T : Enum<T>> searchEnumByName(name: String, ignoreCase: Boolean = true): T? =
    enumValues<T>().find { it.name.equals(name, ignoreCase) }
