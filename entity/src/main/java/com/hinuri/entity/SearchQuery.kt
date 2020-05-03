package com.hinuri.entity

data class SearchQuery(
    val part : String = "snippet", // snippet 또는 id
    var maxResults : Int = 20, // 디폴트 : 5. 허용값 : 0~50
    var order:String = "relevance", // 디폴트 : relevance. date/rating/relevance/title/videoCount/viewCount
    val query:String, // 검색어
    var type :String = "video", // 검색 타입. video/channel/playlist
    var videoEmbeddable:String = "any", // 웹페이지로 동영상을 퍼갈수 있는지 여부. any/true
    var videoSyndicated:String = "any" // youtube.com 외부에서 재생할 수 있는 동영상만 포함 여부. any/true
)