package com.nemesis.rio.presentation.app.messages

import com.nemesis.rio.data.connection.NotConnectedToNetworkException
import com.nemesis.rio.presentation.R
import com.nemesis.rio.presentation.profile.ProfileType
import com.nemesis.rio.presentation.profile.stringResId
import splitties.resources.appStr

sealed class Message {
    class Short(val text: String) : Message()
    class Long(val shortText: String, val fullText: String) : Message()
}

fun profileNotFoundMessage(profileType: ProfileType): Message {
    val profileTypeString = appStr(profileType.stringResId)
    val profileNotFoundMessage = appStr(R.string.search_profile_type_not_found_format, profileTypeString)
    return Message.Short(profileNotFoundMessage)
}

fun profileAlreadyUpdatedMessage() =
    Message.Short(appStr(R.string.message_profile_already_updated))

fun exceptionMessage(exception: Throwable) =
    if (exception is NotConnectedToNetworkException) {
        Message.Short(appStr(R.string.message_no_internet_connection))
    } else {
        Message.Long(
            appStr(R.string.message_unexpected_exception_title),
            appStr(
                R.string.message_unexpected_cause_error_format,
                exception::class.simpleName,
                exception.message
            )
        )
    }
