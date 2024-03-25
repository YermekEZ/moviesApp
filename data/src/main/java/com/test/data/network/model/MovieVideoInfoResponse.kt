package com.test.data.network.model

data class MovieVideoInfoResponse(
    val id: Int,
    val results: List<VideoDetails>?
)

data class VideoDetails(
    val name: String?,
    val key: String?,
    val size: Int?,
    val type: String?,
    val official: Boolean?
)
