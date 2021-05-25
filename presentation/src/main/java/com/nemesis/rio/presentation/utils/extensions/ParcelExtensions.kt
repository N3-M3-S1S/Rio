package com.nemesis.rio.presentation.utils.extensions

import android.os.Parcel
import android.os.Parcelable

fun <T : Enum<T>> Parcel.writeEnum(enum: T?) {
    writeSerializable(enum)
}

inline fun <reified T : Enum<T>> Parcel.readEnum(): T {
    return readSerializable() as T
}

inline fun <reified T : Enum<T>> Parcel.readEnumOrNull(): T? {
    return readSerializable() as T?
}

fun Parcel.readStringOrThrow() =
    requireNotNull(readString())

inline fun <reified T : Parcelable> Parcel.readParcelableOrThrow() =
    requireNotNull(readParcelable<T>(T::class.java.classLoader))

fun Parcel.writeParcelable(parcelable: Parcelable) =
    writeParcelable(parcelable, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
