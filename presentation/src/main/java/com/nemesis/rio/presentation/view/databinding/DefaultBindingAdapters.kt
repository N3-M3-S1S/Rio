package com.nemesis.rio.presentation.view.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingComponent
import com.nemesis.rio.presentation.R
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import splitties.resources.str
import java.time.format.DateTimeFormatter

class DefaultBindingAdapters : DataBindingComponent {

    private lateinit var dateTimeFormatter: DateTimeFormatter

    fun setDateTimeFormatter(formatter: DateTimeFormatter) {
        dateTimeFormatter = formatter
    }

    @BindingAdapter("instant")
    fun setInstant(textView: TextView, instant: Instant?) {
        instant?.let { textView.text = dateTimeFormatter.format(instant.toJavaInstant()) }
    }

    @BindingAdapter("profileOverview_lastUpdate")
    fun TextView.setProfileRefreshDateTime(lastRefreshInstant: Instant?) =
        lastRefreshInstant?.let {
            val formattedDateTime = dateTimeFormatter.format(lastRefreshInstant.toJavaInstant())
            val lastUpdatedFormattedString =
                str(R.string.profile_overall_last_update_datetime_format, formattedDateTime)
            text = lastUpdatedFormattedString
        }

    override fun getDefaultBindingAdapters() = this
}
