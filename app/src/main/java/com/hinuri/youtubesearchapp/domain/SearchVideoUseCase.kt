package com.hinuri.youtubesearchapp.domain

import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.entity.*

class SearchVideoUseCase(
    private val youtubeRepository: YoutubeRepository
) {
    suspend fun invoke(searchQuery: SearchQuery): Result<SearchResult> {
        return youtubeRepository.searchVideos(searchQuery).run {
            if(this is Result.Success && data.nextPageToken.isNotEmpty()) {
                // 로딩할 다음 페이지가 있을 경우, 페이징 처리를 위해 리스트 가장 끝에 프로그래스바뷰를 넣어줌.
                Result.Success(data.apply {
                    items += VideoItem(null, null, null)
                })
            }
            else this
        }
    }
}