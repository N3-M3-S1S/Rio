package com.nemesis.rio.presentation.view.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import com.nemesis.rio.presentation.R
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import splitties.resources.str
import java.time.format.DateTimeFormatter

class DefaultBindingAdapters : DataBindingComponent {

    private lateinit var dateTimeFormatter: DateTimeFormatter

    fun setDateTimeFormatter(formatter: DateTimeFormatter) {
        dateTimeFormatter = formatter
    }

    @BindingAdapter("localDateTime")
    fun setLocalDateTime(textView: TextView, localDateTime: LocalDateTime?) {
        localDateTime?.let { textView.text = it.toJavaLocalDateTime().format(dateTimeFormatter) }
    }

    @BindingAdapter("profileOverview_lastUpdateDateTime")
    fun TextView.setProfileRefreshDateTime(profileRefreshDateTime: LocalDateTime?) =
        profileRefreshDateTime?.let {
            val formattedDateTime = it.toJavaLocalDateTime().format(dateTimeFormatter)
            val lastUpdatedFormattedString =
                str(R.string.profile_overall_last_update_datetime_format, formattedDateTime)
            text = lastUpdatedFormattedString
        }

    override fun getDefaultBindingAdapters() = this
}
