package com.hinuri.youtubesearchapp.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hinuri.youtubesearchapp.R

abstract class BaseFragment<T> : Fragment() {

    internal var viewDataBinding : T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // toolbar 사용할 수 있도록
        setHasOptionsMenu(true)
    }

    /**
     * 툴바 우측의 'x' 클릭 시
     * */
    internal open fun setToolbarClose() {
        requireActivity().finish()
    }

    /**
     * 툴바 좌측의 뒤로가기 버튼 클릭 시
     * */
    internal open fun setToolbarBackButton() {
        requireActivity().onBackPressed()
    }
    // 액션바 아이콘 리스너
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            setToolbarBackButton()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null

        (activity as? AppCompatActivity)?.setSupportActionBar(null)
    }
}