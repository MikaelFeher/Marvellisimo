package com.androidcourse.marvellisimo.models.character

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)