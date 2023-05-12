package com.mitsinsar.scorpcasestudy.peoplelisting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mitsinsar.scorpcasestudy.R
import com.mitsinsar.scorpcasestudy.core.ui.BaseFragment
import com.mitsinsar.scorpcasestudy.core.ui.utils.collectLatestOnLifecycle
import com.mitsinsar.scorpcasestudy.core.ui.utils.recyclerview.LayoutAwareLinearLayoutManager
import com.mitsinsar.scorpcasestudy.core.ui.utils.recyclerview.LayoutAwareLinearLayoutManager.OnLayoutCompletedListener
import com.mitsinsar.scorpcasestudy.core.ui.utils.recyclerview.RecyclerViewEndlessScrollListener
import com.mitsinsar.scorpcasestudy.core.ui.utils.viewbinding.viewBinding
import com.mitsinsar.scorpcasestudy.databinding.FragmentPeopleBinding
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreviewInfoBarAction
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.recycler.PeopleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class PeopleFragment : BaseFragment(R.layout.fragment_people) {

    private val binding by viewBinding(FragmentPeopleBinding::bind)

    private val peopleViewModel by viewModels<PeopleViewModel>()

    private val peopleAdapter = PeopleAdapter()

    private val layoutCompletedListener: OnLayoutCompletedListener = OnLayoutCompletedListener {
        peopleViewModel.onRecyclerItemsInserted(peopleRecyclerViewLayoutManager.isLastItemCompletelyVisible())
    }

    private val peopleRecyclerViewLayoutManager by lazy {
        LayoutAwareLinearLayoutManager(layoutCompletedListener, binding.peopleRecyclerView.context)
    }

    private val peopleItemCollector: suspend (List<PersonItem>?) -> Unit = {
        peopleAdapter.submitList(it.orEmpty())
    }

    private val infoBarActionCollector: suspend (PeoplePreviewInfoBarAction?) -> Unit = { infoBarAction ->
        if (infoBarAction != null) showInfoBar(infoBarAction)
    }

    private val loadingVisibilityCollector: suspend (Boolean?) -> Unit = { isVisible ->
        binding.rootSwipeRefreshLayout.isRefreshing = isVisible == true
    }

    private val endlessScrollLoadMoreListener = RecyclerViewEndlessScrollListener.LoadMoreListener {
        peopleViewModel.onRecyclerBottomItemCountThreshold()
    }

    private val recyclerViewEndlessScrollListener by lazy {
        RecyclerViewEndlessScrollListener(
            layoutManager = peopleRecyclerViewLayoutManager,
            loadMoreThreshold = LOAD_MORE_PEOPLE_THRESHOLD,
            listener = endlessScrollLoadMoreListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        with(binding) {
            peopleRecyclerView.apply {
                adapter = peopleAdapter
                layoutManager = peopleRecyclerViewLayoutManager
                itemAnimator = null
                addOnScrollListener(recyclerViewEndlessScrollListener)
            }
            binding.rootSwipeRefreshLayout.setOnRefreshListener { peopleViewModel.onSwipeToRefresh() }
        }
    }

    private fun initObservers() {
        with(peopleViewModel.peoplePreviewFlow) {
            viewLifecycleOwner.collectLatestOnLifecycle(
                flow = map { it?.peopleItem }.distinctUntilChanged(),
                collector = peopleItemCollector
            )
            viewLifecycleOwner.collectLatestOnLifecycle(
                flow = map { it?.infoBarAction }.distinctUntilChanged(),
                collector = infoBarActionCollector
            )
            viewLifecycleOwner.collectLatestOnLifecycle(
                flow = map { it?.isLoadingVisible }.distinctUntilChanged(),
                collector = loadingVisibilityCollector
            )
        }
    }

    private fun showInfoBar(infoBarAction: PeoplePreviewInfoBarAction) {
        with(infoBarAction) {
            Snackbar.make(binding.rootSwipeRefreshLayout, infoText, visibilityLength)
                .setAction(buttonResId) {
                    peopleViewModel.onInfoBarActionClick()
                }
                .show()
        }
    }

    companion object {
        private const val LOAD_MORE_PEOPLE_THRESHOLD = 5
    }
}
