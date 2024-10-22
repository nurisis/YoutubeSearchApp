package com.hinuri.data

import com.hinuri.entity.SearchResult
import com.hinuri.entity.VideoItem
import com.hinuri.entity.VideoResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// TODO ::
/**
 * # Youtube Data API 문서
 * -> https://developers.google.com/youtube/v3/docs
 *
 * # API 사용 블로그 문서
 * -> https://dev.to/aveb/making-your-first-get-request-to-youtube-search-api-4c2f
 *
 * # video 영상 가져오기
 * -> https://www.youtube.com/embed/[video-id]
 * */

interface YoutubeApi {

    @GET("search")
    suspend fun searchVideo(
        @Query("key")key:String = Constant.GOOGLE_API_KEY,
        @Query("part")part:String,
        @Query("q")query:String,
        @Query("pageToken")pageToken:String,
        @Query("order")order:String,
        @Query("maxResults")maxResults:Int,
        @Query("type")type:String,
        @Query("videoEmbeddable")videoEmbeddable:String,
        @Query("videoSyndicated")videoSyndicated:String
    ): Response<SearchResult>

    @GET("videos")
    suspend fun getVideo(
        @Query("key")key:String = Constant.GOOGLE_API_KEY,
        @Query("part")part:String,
//        @Query("chart")chart:String,
        @Query("pageTokiden")pageToken:String,
        @Query("maxResults")maxResults:Int,
        @Query("id")id:String
    ): Response<VideoResult>
}