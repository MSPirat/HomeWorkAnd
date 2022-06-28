package ru.netology.nmedia

object CountLikeShare {

    private fun letterToCount(value: Long): String {
        return if (value in 1_000..999_999) {
            "K"
        } else if (value >= 1_000_000) {
            "M"
        } else {
            ""
        }
    }

    fun counter(value: Long): String {
        val result = when(value) {
            in 0..999 -> value
            in 1_000..999_999 -> value / 1000
            in 1_000_000..999_999_999 -> value / 1_000_000
            else -> 0
        }
        return result.toString() + letterToCount(value)
    }

}