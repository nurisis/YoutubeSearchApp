package com.hinuri.youtubesearchapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentVideoDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VideoDetailFragment : BaseFragment<FragmentVideoDetailBinding>() {
    private val viewModel:SearchViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refreshVideoDetailData()

        // 리스트 화면으로 부터 videoId 전달받음. => 이 videoId로 영상 상세 정보 불러옴.
        arguments?.apply {
            viewModel.getVideo(videoId = getString(EXTRA_VIDEO_ID, ""))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =  FragmentVideoDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@VideoDetailFragment.viewModel
        }

        setUpWebView()

        return viewDataBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO ::여기 지워주자
//        viewDataBinding?.webview?.loadUrl("https://www.youtube.com/embed/${viewModel.videoDetailItemId}?loop=1&playlist=${viewModel.videoDetailItemId}")
        viewDataBinding?.webview?.loadUrl(  getString(R.string.embedded_video_url, viewModel.videoDetailItemId, viewModel.videoDetailItemId))
    }

    /**
     * 유튜브 영상을 보여줄 webview 셋팅 작업
     * */
    private fun setUpWebView() {
        viewDataBinding?.webview?.apply {
            setBackgroundColor(Color.BLACK)
            webViewClient = customWebViewClient
            setNetworkAvailable(true)
            clearCache(true)
            // TODO :: 이거 필요없음?
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }
            }
        }

        viewDataBinding?.webview?.settings?.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true // Allow a metatag
            useWideViewPort = true // Fit a screen size
            setSupportZoom(true) // Zoom of screen
            builtInZoomControls = true // Zoom in or out of screen
            cacheMode = WebSettings.LOAD_NO_CACHE // Cache of browser
        }
    }

    private val customWebViewClient = object : WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            // embedded 영상이 아닌 유튜브 웹페이지로 이동하는 경우는 막음. 왜냐하면 여기서의 웹뷰 용도는 유튜브의 embedded 영상을 보여주기 위한 것이기 때문.
            if(request?.url?.path?.contains("embed") != true) {
                Toast.makeText(requireContext(), getString(R.string.error_youtube_not_implement), Toast.LENGTH_LONG).show()
                return true
            }

            return super.shouldOverrideUrlLoading(view, request)
        }
    }

}
