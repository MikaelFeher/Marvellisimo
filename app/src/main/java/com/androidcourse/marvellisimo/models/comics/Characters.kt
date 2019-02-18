package com.androidcourse.marvellisimo.models.comics

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)