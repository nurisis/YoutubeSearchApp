package com.hinuri.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoItem(
    @SerializedName("etag") val etag: String,
    @SerializedName("id") val id: VideoId,
    @SerializedName("snippet") val snippet: VideoSnippet,
    @SerializedName("channelTitle") val channelTitle: String,
    @SerializedName("liveBroadcastContent") val liveBroadcastContent: String
) : Serializable

data class VideoSnippet(
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("channelId") val channelId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: VideoThumbnail
) : Serializable

data class VideoThumbnail(
    @SerializedName("medium") val medium: Thumbnail
) : Serializable

data class Thumbnail(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
) : Serializable

data class VideoId(
    @SerializedName("videoId") val videoId: String
) : Serializable
