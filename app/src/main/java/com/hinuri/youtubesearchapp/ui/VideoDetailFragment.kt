package com.hinuri.youtubesearchapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hinuri.entity.VideoItem
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentVideoDetailBinding
import kotlinx.android.synthetic.main.fragment_video_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoDetailFragment : BaseFragment<FragmentVideoDetailBinding>() {
    private val viewModel:SearchViewModel by viewModel()
    private var videoItem:VideoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            videoItem = getSerializable("item") as VideoItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =  FragmentVideoDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            videoItem = this@VideoDetailFragment.videoItem
        }

        setUpWebView()

        return viewDataBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
        })

        viewDataBinding?.webview?.loadUrl("https://www.youtube.com/embed/${videoItem?.id?.videoId ?: ""}?loop=1&playlist=${videoItem?.id?.videoId ?: ""}".also {
            Log.d("LOG>>", "동영상 url : $it")
        })
    }


    private fun setUpWebView() {
        viewDataBinding?.webview?.apply {
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
