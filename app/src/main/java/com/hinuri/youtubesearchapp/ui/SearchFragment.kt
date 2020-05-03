package com.hinuri.youtubesearchapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hinuri.youtubesearchapp.R
import com.hinuri.youtubesearchapp.base.BaseFragment
import com.hinuri.youtubesearchapp.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel:SearchViewModel by viewModel()

    private var listAdapter : VideoListAdapter? = null
    private var inputMethodManager: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =  FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner

            etSearch.setOnEditorActionListener(keyboardActionListener)

            listAdapter = VideoListAdapter()
            rvVideos.apply {
                adapter = listAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        }
        return viewDataBinding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 툴바 설정
        (requireActivity() as? MainActivity)?.setToolBar(getString(R.string.toolbar_search),
            isHomeButton = false,
            isCloseButton = false
        )

        inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel.toastMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
        })

        viewModel.videoList.observe(viewLifecycleOwner, Observer {
            listAdapter?.submitList(it)
        })
    }

    /**
     * Edittext 검색 버튼 리스너
     * */
    private val keyboardActionListener = TextView.OnEditorActionListener { v, actionId, _ ->
        if(actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.searchVideo(viewDataBinding?.etSearch?.text?.toString() ?: "")
            // 키보드 숨김
            inputMethodManager?.hideSoftInputFromWindow(v.windowToken, 0)
        }

        return@OnEditorActionListener true
    }

    override fun onDestroyView() {
        listAdapter = null
        inputMethodManager = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
