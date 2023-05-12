package com.mitsinsar.scorpcasestudy.core.ui.utils.recyclerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LayoutAwareLinearLayoutManager(
    private val listener: OnLayoutCompletedListener,
    context: Context
) : LinearLayoutManager(context) {

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        listener.onLayoutCompleted()
    }

    fun isLastItemCompletelyVisible() = findLastCompletelyVisibleItemPosition() == itemCount - 1

    fun interface OnLayoutCompletedListener {
        fun onLayoutCompleted()
    }
}
