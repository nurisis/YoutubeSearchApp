package com.hinuri.youtubesearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hinuri.entity.VideoItem
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentVideoDetailBinding
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
        return viewDataBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 툴바 설정
        (requireActivity() as? MainActivity)?.setToolBar(getString(R.string.toolbar_video_detail),
            isHomeButton = false,
            isCloseButton = false
        )

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoDetailFragment()
    }
}
