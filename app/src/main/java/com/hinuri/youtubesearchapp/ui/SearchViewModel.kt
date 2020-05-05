package com.hinuri.youtubesearchapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hinuri.data.repository.YoutubeRepository
import com.hinuri.entity.*
import com.hinuri.entity.Constant.NUM_OF_VIDEO_LIST_PER_LOADING
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.domain.GetVideoUseCase
import com.hinuri.youtubesearchapp.domain.SearchVideoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable

class SearchViewModel(
    private val searchVideoUseCase: SearchVideoUseCase,
    private val getVideoUseCase: GetVideoUseCase
) :ViewModel(){
    private val TAG = "LOG>>"

    private val _toastMessage = MutableLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _videoList = MutableLiveData<List<VideoItem>>()
    val videoList: LiveData<List<VideoItem>> = _videoList

    private var query : String? = null
    private var nextPageToken:String? = null
    // 페이징 처리를 위한
    private var isLastData = true
    private var gridLockView = false

    var videoDetailItemId = ""
    private val _videoDetailItem = MutableLiveData<VideoResult>()
    val videoDetailItem: LiveData<VideoResult> = _videoDetailItem

    fun searchVideo(query:String) {
        if(query.isBlank()) return

        this.query = query
        isLastData = false

        _loading.value = true
        viewModelScope.launch {
            searchVideoUseCase.invoke(SearchQuery(query = query)).apply {
                _loading.value = false

                Log.d(TAG, "searchVideo : $this")
                when(this) {
                    is Result.Success ->  {
                        _videoList.value = data.items

                        nextPageToken = data.nextPageToken
                        isLastData = data.nextPageToken.isEmpty()
                    }
                    is Result.Error -> {
                        _toastMessage.value =
                            if(exception.message?.contains("quotaExceeded") == true || exception.message?.contains("usageLimits") == true)
                                R.string.error_get_search_result_limit
                            else R.string.error_get_search_result

                        // temp - test
                        nextPageToken = "TEst"
                        _videoList.value = listOf(
                            VideoItem("test1", VideoId(videoId = "2"),
                                VideoSnippet("", "", "Test 6", "",
                                    VideoThumbnail(Thumbnail("Test",0,0), Thumbnail("Test",0,0)), "Channel1", "")),
                            VideoItem("test2", VideoId(videoId = "2"),
                                VideoSnippet("", "", "Test 7", "",
                                    VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", "")),
                            VideoItem("test1", VideoId(videoId = "2"),
                                VideoSnippet("", "", "Test 8", "",
                                    VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", "")),
                            VideoItem("test1", VideoId(videoId = "2"),
                                VideoSnippet("", "", "Test 9", "",
                                    VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1","")),
                            VideoItem("test1", VideoId(videoId = "2"),
                                VideoSnippet("", "", "Test 10", "",
                                    VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", ""))
                        )
                    }
                }
            }
        }
    }

    fun loadModeVideo() {
        Log.d(TAG, "in loadMoreVideo >> $query , $nextPageToken")
        if(query == null || nextPageToken == null) return

        if(isLastData || gridLockView) return

        gridLockView = true
        viewModelScope.launch {

            searchVideoUseCase.invoke(SearchQuery(query = query!!, pageToken = nextPageToken!!)).apply {

                Log.d(TAG, "비디오 로딩 더보기 : $this")
                when(this) {
                    is Result.Success ->  {

                        _videoList.value = videoList.value?.toMutableList()?.apply {
                            remove(VideoItem(null, null, null))
                            addAll(data.items)
                        } ?: data.items

                        gridLockView = false

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
                            // temp - test
                            addAll(listOf(
                                VideoItem("test1", VideoId(videoId = "2"),
                                    VideoSnippet("", "", "Test 6", "",
                                        VideoThumbnail(Thumbnail("Test",0,0), Thumbnail("Test",0,0)), "Channel1", "")),
                                VideoItem("test2", VideoId(videoId = "2"),
                                    VideoSnippet("", "", "Test 7", "",
                                        VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", "")),
                                VideoItem("test1", VideoId(videoId = "2"),
                                    VideoSnippet("", "", "Test 8", "",
                                        VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", "")),
                                VideoItem("test1", VideoId(videoId = "2"),
                                    VideoSnippet("", "", "Test 9", "",
                                        VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1","")),
                                VideoItem("test1", VideoId(videoId = "2"),
                                    VideoSnippet("", "", "Test 10", "",
                                        VideoThumbnail(Thumbnail("Test",0,0),Thumbnail("Test",0,0)), "Channel1", ""))
                            ))
                            if(!isLastData)
                                add(VideoItem(null, null, null))
                        }

                        // TODO ::
//                        isLastData = true
                        gridLockView = false
                    }
                }
            }
        }
    }

    // TODO :: 여기서부터 구현해야.
    fun getVideo(videoId : String) {
        if(videoId.isEmpty()) return

        videoDetailItemId = videoId
        viewModelScope.launch {
            getVideoUseCase.invoke(VideoQuery(id = videoId)).apply {
                Log.d(TAG, "상세 정보 : $this")
                when(this) {
                    is Result.Success -> _videoDetailItem.value = data
                    // TODO :: 예외처리 해줘야 함.
                }
            }
        }
    }

    /**
     * ViewModel이 죽을 때 호출됨 ==> 죽기 전 먼저 사용자 정보를 저장해놓음.
     * 저장할 데이터
     * -
     * */
    fun getVMDataToSave() : Bundle {
        return Bundle().apply {
            query?.let { putString("query", it) }
        }
    }

    /**
     *  기존에 저장된 데이터가있으면 다시 담아줌 (시스템 kill 당했을 때)
     * */
    fun restoreVMData(bundle: Bundle?) {
        bundle?.run {
            getString("query")?.let {
                query = it
                searchVideo(it)
            }
        }
    }
}