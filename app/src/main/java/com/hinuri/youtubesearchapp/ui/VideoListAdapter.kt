package com.hinuri.youtubesearchapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hinuri.entity.VideoItem
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.databinding.ItemVideoListBinding

const val EXTRA_VIDEO_ID = "videoId"

class VideoListAdapter : ListAdapter<VideoItem, RecyclerView.ViewHolder>(
    VideoListDiffCallback()
) {

    private val typeProgressBar = 0 // 페이징 시 보이는 하단 프로그래스 바
    private val typeItem = 1

    override fun getItemViewType(position: Int): Int {
        return getItem(position).run {
            if(etag == null && id == null)
                typeProgressBar
            else typeItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  {
        return if(viewType == typeProgressBar)
            ProgressbarHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_bottom_progressbar,
                    parent,
                    false
                )
            )
        else
            ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder) holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ItemVideoListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(item: VideoItem) {
            binding.apply {
                setItem(item)
                lifecycleOwner = binding.root.context as LifecycleOwner
            }.executePendingBindings()
        }

        // 리스트 클릭 시 해당 비디오의 상세화면으로 이동 (videoId 전달)
        override fun onClick(v: View?) {
            v?.let {
                it.findNavController().navigate(
                    R.id.action_searchFragment_to_videoDetailFragment,
                    Bundle().apply { putString(EXTRA_VIDEO_ID, binding.item?.id?.videoId) }
                )
            }
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                return ViewHolder(
                    ItemVideoListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false)
                )
            }
        }
    }
}

private class VideoListDiffCallback : DiffUtil.ItemCallback<VideoItem>() {
    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
        return oldItem.id?.equals(newItem.id) ?: false
    }

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
        return oldItem.id?.equals(newItem.id) ?: false
    }
}

class ProgressbarHolder(progressBarView: View) : RecyclerView.ViewHolder(progressBarView)