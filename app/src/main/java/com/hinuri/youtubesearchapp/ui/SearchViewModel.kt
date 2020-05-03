package com.hinuri.youtubesearchapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.entity.Result
import com.hinuri.entity.SearchQuery
import com.hinuri.entity.VideoItem
import com.hinuri.youtubesearchapp.R
import kotlinx.coroutines.launch

class SearchViewModel(
    private val youtubeRepository: YoutubeRepository
) :ViewModel(){
    private val TAG = "LOG>>"

    private val _toastMessage = MutableLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _videoList = MutableLiveData<List<VideoItem>>()
    val videoList: LiveData<List<VideoItem>> = _videoList

    fun searchVideo(query:String) {
        if(query.isBlank()) return

        _loading.value = true
        viewModelScope.launch {
            youtubeRepository.searchVideos(SearchQuery(query = query)).apply {
                _loading.value = false

                Log.d(TAG, "searchVideo : $this")
                when(this) {
                    is Result.Success -> _videoList.value = data.items
                    is Error -> {
                        _toastMessage.value = R.string.error_get_search_result
                        _videoList.value = emptyList()
                    }
                }
            }
        }
    }
}