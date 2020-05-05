package com.hinuri.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoDetailItem(
    @SerializedName("etag") val etag: String?,
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val snippet: VideoSnippet?,
    @SerializedName("statistics") val statistics: VideoStatistics?
) : Serializable