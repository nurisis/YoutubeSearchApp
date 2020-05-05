package com.hinuri.youtubesearchapp.domain

import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.entity.Result
import com.hinuri.entity.SearchResult
import com.hinuri.entity.VideoQuery
import com.hinuri.entity.VideoResult

class GetVideoUseCase(
    private val youtubeRepository: YoutubeRepository
) {
    suspend fun invoke(videoQuery: VideoQuery) : Result<VideoResult> {
        return youtubeRepository.getVideos(videoQuery)
    }
}