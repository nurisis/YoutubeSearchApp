package com.hinuri.entity

import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("etag") val etag:String,
    @SerializedName("nextPageToken") val nextPageToken:String,
    @SerializedName("regionCode") val regionCode:String,
    @SerializedName("pageInfo") val pageInfo:VideoPageInfo,
    @SerializedName("items") var items:List<VideoDetailItem>
)