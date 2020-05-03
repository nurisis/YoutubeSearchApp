package com.hinuri.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoItem(
    @SerializedName("etag") val etag: String,
    @SerializedName("id") val id: VideoId,
    @SerializedName("snippet") val snippet: VideoSnippet,
    @SerializedName("liveBroadcastContent") val liveBroadcastContent: String
) : Serializable

data class VideoSnippet(
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("channelId") val channelId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: VideoThumbnail,
    @SerializedName("channelTitle") val channelTitle: String
) : Serializable

data class VideoThumbnail(
    @SerializedName("medium") val medium: Thumbnail,
    @SerializedName("high") val high: Thumbnail
) : Serializable

data class Thumbnail(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
) : Serializable

data class VideoId(
    @SerializedName("videoId") val videoId: String
) : Serializable
