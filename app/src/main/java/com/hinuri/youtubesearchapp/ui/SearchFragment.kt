package com.hinuri.youtubesearchapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel:SearchViewModel by sharedViewModel()

    private var listAdapter : VideoListAdapter? = null
    private var inputMethodManager: InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =  FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchFragment.viewModel

            etSearch.setOnEditorActionListener(keyboardActionListener)

            listAdapter = VideoListAdapter()
            rvVideos.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                addOnScrollListener(scrollListener)
            }

            ivErase.setOnClickListener { etSearch.text.clear() }
        }
        return viewDataBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel.videoList.observe(viewLifecycleOwner, Observer {
            listAdapter?.submitList(it)
        })
    }

    // 영상 페이징 처리를 위한 스크롤 리스너
    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var lastVisiblePosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition() ?: 0
            var itemTotalCount = recyclerView.adapter?.itemCount ?: 0

            // 바닥일 때
            if(lastVisiblePosition+1 == itemTotalCount)
                viewModel.loadModeVideo()
        }
    }

    /**
     * Edittext 검색 버튼 리스너
     * */
    private val keyboardActionListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.searchVideo(viewDataBinding?.etSearch?.text?.toString() ?: "")
            // 키보드 숨김
            inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
            viewDataBinding?.etSearch?.clearFocus()
        }

        return@OnEditorActionListener true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(viewModel.getVMDataToSave())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        viewModel.restoreVMData(savedInstanceState)
    }

    override fun onPause() {
        // 키보드 내림
        viewDataBinding?.run {
            inputMethodManager?.hideSoftInputFromWindow(root.windowToken, 0)
        }
        super.onPause()
    }

    override fun onDestroyView() {
        listAdapter = null
        inputMethodManager = null
        super.onDestroyView()
    }
}
