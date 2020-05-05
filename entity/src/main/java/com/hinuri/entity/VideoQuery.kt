package com.hinuri.entity

/**
 * https://developers.google.com/youtube/v3/docs/videos/list?apix_params=%7B%22part%22%3A%22id%2C%20snippet%2C%20contentDetails%2C%20fileDetails%2C%20liveStreamingDetails%2C%20player%2C%20processingDetails%2C%20recordingDetails%2C%20statistics%2C%20status%2C%20suggestions%2C%20topicDetails%22%2C%22id%22%3A%22T-U3c1nU3eM%22%7D#%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98
 * */

data class VideoQuery(
    val part : String = "snippet, statistics", // snippet, id, contentDetails, fileDetails, liveStreamingDetails, player, processingDetails, statistics, topicDetails, suggestions
//    var chart : String = "",
    var id : String = "",
    var maxResults : Int = 1, // 1 ~ 50
    var pageToken:String = ""
)