package com.hinuri.entity

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("etag") val etag:String,
    @SerializedName("nextPageToken") val nextPageToken:String,
    @SerializedName("regionCode") val regionCode:String,
    @SerializedName("pageInfo") val pageInfo:VideoPageInfo,
    @SerializedName("items") val items:List<VideoItem>
)

data class VideoPageInfo(
    @SerializedName("totalResults") val totalResults:Int,
    @SerializedName("resultsPerPage") val resultsPerPage:Int
)