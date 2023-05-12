package com.mitsinsar.scorpcasestudy.core.ui.utils.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEndlessScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMoreThreshold: Int,
    private val listener: LoadMoreListener
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isScrolledDown(dy) && shouldLoadMore()) {
            listener.loadMore()
        }
    }

    private fun isScrolledDown(dy: Int) = dy > 0

    private fun shouldLoadMore(): Boolean {
        return (layoutManager.findLastVisibleItemPosition() + loadMoreThreshold) >= layoutManager.itemCount
    }

    fun interface LoadMoreListener {
        fun loadMore()
    }
}
