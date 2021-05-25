package com.nemesis.rio.presentation.utils

private const val SEPARATOR = ","
private const val KEY_VALUE_DELIMITER = "="

fun <K, V> Map<K, V>.toSharedPreferencesString(
    keyToStringTransform: (K) -> String,
    valueToStringTransform: (V) -> String,
) =
    map { (key, value) ->
        keyToStringTransform(key) + KEY_VALUE_DELIMITER + valueToStringTransform(value)
    }.joinToString(SEPARATOR)

fun <K, V> sharedPreferencesStringToMap(
    sharedPreferencesString: String,
    stringToKeyTransform: (String) -> K,
    stringToValueTransform: (String) -> V,
    destination: MutableMap<K, V> = mutableMapOf(),
): MutableMap<K, V> {
    if (sharedPreferencesString.isNotEmpty()) {
        sharedPreferencesString
            .split(SEPARATOR)
            .forEach {
                val (key, value) = it.split(KEY_VALUE_DELIMITER)
                destination[stringToKeyTransform(key)] = stringToValueTransform(value)
            }
    }
    return destination
}
