package com.hinuri.youtubesearchapp.domain

import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.entity.*

class SearchVideoUseCase(
    private val youtubeRepository: YoutubeRepository
) {
    suspend fun invoke(searchQuery: SearchQuery): Result<SearchResult> {
        return youtubeRepository.searchVideos(searchQuery).run {
            if(this is Result.Success && data.nextPageToken.isNotEmpty()) {
                Result.Success(data.apply {
                    items += VideoItem(null, null, null)
                })
            }
            else this
        }
    }
}