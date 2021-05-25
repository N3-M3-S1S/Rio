package com.nemesis.rio.presentation.utils.extensions

import android.os.Bundle

fun <E : Enum<E>> Bundle.getEnum(key: String) = requireNotNull(getEnumOrNull<E>(key))

@Suppress("UNCHECKED_CAST")
fun <E : Enum<E>?> Bundle.getEnumOrNull(key: String) = get(key) as? E
