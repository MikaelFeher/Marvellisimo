package com.androidcourse.marvellisimo.models.character

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)