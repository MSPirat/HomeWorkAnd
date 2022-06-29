package ru.netology.nmedia

import java.math.RoundingMode
import java.text.DecimalFormat

object CountLikeShare {

    private const val ZERO = 0f
    private const val THOUSAND = 1_000f
    private const val TEN_THOUSAND = 10_000f
    private const val HUNDRED_THOUSAND = 100_000f
    private const val MILLION = 1_000_000f

    private fun letterToCount(value: Float): String {
        return if (value in THOUSAND..(MILLION - 1f)) {
            "K"
        } else if (value >= MILLION) {
            "M"
        } else {
            ""
        }
    }

    private fun counter(value: Float): String {
        val result = when (value) {
            in ZERO..(THOUSAND - 1f) -> value
            in THOUSAND..(MILLION - 1f) -> value / THOUSAND
            in MILLION..Int.MAX_VALUE.toFloat() -> value / MILLION
            else -> ZERO
        }
        return result.toLong().toString()
    }

    private fun roundingToInteger(value: Float): String {
        val result = DecimalFormat("#")
        result.roundingMode = RoundingMode.DOWN
        return result.format(value)
    }

    private fun roundingToDecimal(value: Float): String {
        val result = DecimalFormat("#.#")
        result.roundingMode = RoundingMode.DOWN
        return result.format(value)
    }

    fun counterDecimal(value: Float): String {
        val valueDivThousand = value / THOUSAND
        val valueDivMillion = value / MILLION

        val result = if (value in THOUSAND..(TEN_THOUSAND - 1f) && value % THOUSAND != ZERO) {
            roundingToDecimal(valueDivThousand)
        } else if (value in TEN_THOUSAND..(HUNDRED_THOUSAND - 1f) && value % THOUSAND != ZERO) {
            roundingToInteger(valueDivThousand)
        } else if (value in HUNDRED_THOUSAND..(MILLION - 1f) && value % THOUSAND != ZERO) {
            roundingToInteger(valueDivThousand)
        } else if (value >= MILLION) {
            roundingToDecimal(valueDivMillion)
        } else counter(value)

        return result + letterToCount(value)
    }
}