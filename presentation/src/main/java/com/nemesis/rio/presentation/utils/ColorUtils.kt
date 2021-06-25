package com.nemesis.rio.presentation.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import com.nemesis.rio.domain.mplus.scores.color.HexColor
import com.nemesis.rio.presentation.R
import splitties.resources.appColor
import timber.log.Timber

fun increaseForegroundHexColorBrightnessToWCAGAAStandard(
    foregroundHexColor: HexColor,
    backgroundColor: Int = appColor(R.color.background)
): Int = increaseForegroundColorBrightnessToWCAGAAStandard(
    Color.parseColor(foregroundHexColor),
    backgroundColor
)

fun increaseForegroundColorBrightnessToWCAGAAStandard(
    foregroundColor: Int,
    backgroundColor: Int = appColor(R.color.background)
): Int {
    val wcagAARecommendedContrastRatio = 4.5
    var resultForegroundColor = foregroundColor
    var currentContrastRatio = ColorUtils.calculateContrast(resultForegroundColor, backgroundColor)

    Timber.d("Contrast ratio between foreground and background: $currentContrastRatio")
    if (currentContrastRatio < wcagAARecommendedContrastRatio) {
        Timber.d("Contrast ratio is less than recommended, increasing brightness of foreground color...")
        val resultForegroundColorHSL = FloatArray(3)
        ColorUtils.colorToHSL(resultForegroundColor, resultForegroundColorHSL)

        val colorLightnessIndex = 2
        val colorLightnessPercent = resultForegroundColorHSL[colorLightnessIndex] / 100

        var colorLightnessIncreasesCount = 0
        do {
            colorLightnessIncreasesCount++
            resultForegroundColorHSL[colorLightnessIndex] += colorLightnessPercent
            resultForegroundColor = ColorUtils.HSLToColor(resultForegroundColorHSL)
            currentContrastRatio =
                ColorUtils.calculateContrast(resultForegroundColor, backgroundColor)
        } while (currentContrastRatio < wcagAARecommendedContrastRatio)

        Timber.d("Brightness of foreground color increased by $colorLightnessIncreasesCount%")
        Timber.d("Result contrast ratio between foreground and background: $currentContrastRatio")
    }
    return resultForegroundColor
}
