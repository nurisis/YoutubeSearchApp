package com.hinuri.data

import com.hinuri.entity.YoutubeSearchResult
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

    @Headers("X-Naver-Client-Id:${Constant.NAVER_CLIENT_ID}", "X-Naver-Client-Secret:${Constant.NAVER_CLIENT_SECRET}")
    @GET("/v1/search/shop.json")
    suspend fun searchCloth(
        @Query("query")query:String, // search query
        @Query("start")start:Int = 1,
        @Query("display")display:Int = 100, // number of results
        @Query("sort")sort:String = "sim" // sort = sim(default) , date, asc(by price), dsc(by price)
    ): Response<YoutubeSearchResult>

}