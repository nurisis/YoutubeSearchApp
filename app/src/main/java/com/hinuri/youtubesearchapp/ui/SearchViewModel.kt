package com.hinuri.youtubesearchapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hinuri.entity.*
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.domain.GetVideoUseCase
import com.hinuri.youtubesearchapp.domain.SearchVideoUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchVideoUseCase: SearchVideoUseCase,
    private val getVideoUseCase: GetVideoUseCase
) :ViewModel(){
    private val TAG = "LOG>>"

    private val _toastMessage = MutableLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // 키워드 검색 결과
    private val _videoList = MutableLiveData<List<VideoItem>>()
    val videoList: LiveData<List<VideoItem>> = _videoList

    // 사용자가 검색한 검색어
    var query : String? = null
    // 검색 결과 호출 시 다음 페이지 로드하기 위한 키값
    private var nextPageToken:String? = null
    // 페이징 처리를 위한
    private var isLastData = false // 검색결과의 마지막 페이지까지 로드했을 경우 true
    private var gridLockView = false // 현재 데이터를 호출중일 때 true

    // 사용자가 결과 목록에서 클릭한 영상의 videoId
    var videoDetailItemId = ""
    // 사용자가 결과 목록에서 클릭한 영상의 상세정보
    private val _videoDetailItem = MutableLiveData<VideoResult>()
    val videoDetailItem: LiveData<VideoResult> = _videoDetailItem

    fun searchVideo(query:String) {
        if(query.isBlank()) return

        this.query = query

        isLastData = false
        gridLockView = true

        _loading.value = true
        viewModelScope.launch {
            searchVideoUseCase.invoke(SearchQuery(query = query)).apply {
                _loading.value = false
                gridLockView = false

                when(this) {
                    is Result.Success ->  {
                        _videoList.value = data.items

                        nextPageToken = data.nextPageToken
                        isLastData = data.nextPageToken.isEmpty()
                    }
                    is Result.Error -> {
                        _toastMessage.value =
                            // 유튜브 API의 하루 사용가능한 용량을 넘겼을 때 ..... => 매일 오후 4시에 새로 리셋됨.
                            if(exception.message?.contains("quotaExceeded") == true || exception.message?.contains("usageLimits") == true)
                                R.string.error_get_search_result_limit
                            else R.string.error_get_search_result
                    }
                }
            }
        }
    }

    // 검색 결과 추가 로딩(페이징).
    // nextPageToken을 이용해 다음 페이지를 로드함.
    fun loadModeVideo() {
        if(query == null || nextPageToken == null) return

        if(isLastData || gridLockView) return

        gridLockView = true
        viewModelScope.launch {

            searchVideoUseCase.invoke(SearchQuery(query = query!!, pageToken = nextPageToken!!)).apply {
                gridLockView = false

                when(this) {
                    is Result.Success ->  {
                        _videoList.value = videoList.value?.toMutableList()?.apply {
                            remove(VideoItem(null, null, null)) // 이전 리스트에 들어있던 페이징용 아이템 삭제
                            addAll(data.items) // 새로 로딩한 아이템 추가
                        }?.distinctBy { it.id?.videoId } // 중복되는 영상 체크
                            ?: data.items

                        nextPageToken = data.nextPageToken
                        isLastData = data.nextPageToken.isEmpty()
                    }
                    is Result.Error -> {
                        _toastMessage.value =
                            if(exception.message?.contains("quotaExceeded") == true || exception.message?.contains("usageLimits") == true)
                                R.string.error_get_search_result_limit
                            else R.string.error_get_search_result

                        _videoList.value =  videoList.value?.toMutableList()?.apply {
                            remove(VideoItem(null, null, null))
                        }

                    }
                }
            }
        }
    }

    // 영상 상세 정보 호출
    fun getVideo(videoId : String) {
        if(videoId.isEmpty()) return

        videoDetailItemId = videoId
        viewModelScope.launch {
            getVideoUseCase.invoke(VideoQuery(id = videoId)).apply {
                when(this) {
                    is Result.Success -> _videoDetailItem.value = data
                    is Result.Error -> _toastMessage.value = R.string.error_get_video_info
                }
            }
        }
    }

    // 영상 상세정보 초기화
    fun refreshVideoDetailData() {
        videoDetailItemId = ""
        _videoDetailItem.value = null
    }

    // ViewModel이 죽을 때 호출됨
    fun getVMDataToSave() : Bundle {
        return Bundle().apply {
            query?.let { putString("query", it) }
        }
    }

    // 기존에 저장된 데이터가있으면 다시 담아줌 (시스템 kill 당했을 때)
    fun restoreVMData(bundle: Bundle?) {
        bundle?.run {
            getString("query")?.let {
                query = it
                searchVideo(it)
            }
        }
    }
}