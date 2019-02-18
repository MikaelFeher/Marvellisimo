package com.androidcourse.marvellisimo.models.comics

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)