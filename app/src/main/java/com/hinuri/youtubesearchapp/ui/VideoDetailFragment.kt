package com.hinuri.youtubesearchapp.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import com.hinuri.entity.VideoItem
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentVideoDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class VideoDetailFragment : BaseFragment<FragmentVideoDetailBinding>() {
    private val viewModel:SearchViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            // tODO
            val videoItem = getSerializable("item") as VideoItem
            viewModel.getVideo(videoId = videoItem?.id?.videoId ?: "")
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

        viewDataBinding?.webview?.loadUrl("https://www.youtube.com/embed/${viewModel.videoDetailItemId}?loop=1&playlist=${viewModel.videoDetailItemId}".also {
            Log.d("LOG>>", "동영상 url : $it")
        })
    }


    private fun setUpWebView() {
        viewDataBinding?.webview?.apply {
            setBackgroundColor(Color.BLACK)
            webViewClient = customWebViewClient
            setNetworkAvailable(true) // Informs WebView of the network state
            clearCache(true)
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }
            }
        }

        // Setting of webview
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
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if(request?.url?.path?.contains("embed") != true) {
                Toast.makeText(requireContext(), getString(R.string.error_youtube_not_implement), Toast.LENGTH_LONG).show()
                Log.e("LOG>>","유튜브로 넘어가는 url >> ${request?.url?.path}")
                return true
            }

            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoDetailFragment()
    }
}
