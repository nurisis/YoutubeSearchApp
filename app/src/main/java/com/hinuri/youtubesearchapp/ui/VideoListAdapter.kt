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

class VideoListAdapter() : ListAdapter<VideoItem, VideoListAdapter.ViewHolder>(
    VideoListDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
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

        override fun onClick(v: View?) {
            v?.let {
                it.findNavController().navigate(
                    R.id.action_searchFragment_to_videoDetailFragment,
                    Bundle().apply { putSerializable("item", binding.item) }
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
        return oldItem.id.videoId == newItem.id.videoId
    }

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
        return oldItem == newItem
    }
}