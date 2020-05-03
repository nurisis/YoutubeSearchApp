package com.hinuri.data.repository

import com.hinuri.data.YoutubeApi
import com.hinuri.data.base.BaseRepository
import com.hinuri.entity.Result
import com.hinuri.entity.SearchQuery
import com.hinuri.entity.SearchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepository (
    private val apiSource: YoutubeApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :BaseRepository() {

    suspend fun searchVideos(searchQuery: SearchQuery) :Result<SearchResult> = withContext(ioDispatcher) {
        return@withContext getResult {
            apiSource.searchVideo(
                part = searchQuery.part,
                query = searchQuery.query,
                maxResults = searchQuery.maxResults,
                order = searchQuery.order,
                type = searchQuery.type,
                videoEmbeddable = searchQuery.videoEmbeddable,
                videoSyndicated = searchQuery.videoSyndicated
            )
        }
    }
}
