package com.hinuri.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoItem(
    @SerializedName("etag") val etag: String?,
    @SerializedName("id") val id: VideoId?,
    @SerializedName("snippet") val snippet: VideoSnippet?
) : Serializable

data class VideoSnippet(
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("channelId") val channelId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnails") val thumbnails: VideoThumbnail,
    @SerializedName("channelTitle") val channelTitle: String,
    @SerializedName("categoryId") val categoryId: String,
    @SerializedName("liveBroadcastContent") var liveBroadcastContent: String = "none"
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

data class VideoStatistics(
    @SerializedName("viewCount") val viewCount: String,
    @SerializedName("likeCount") val likeCount: String,
    @SerializedName("dislikeCount") val dislikeCount: String,
    @SerializedName("favoriteCount") val favoriteCount: String,
    @SerializedName("commentCount") val commentCount: String
) : Serializable