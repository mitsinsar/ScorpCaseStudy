package com.mitsinsar.scorpcasestudy.peoplelisting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mitsinsar.scorpcasestudy.R
import com.mitsinsar.scorpcasestudy.core.ui.BaseFragment
import com.mitsinsar.scorpcasestudy.core.ui.utils.collectLatestOnLifecycle
import com.mitsinsar.scorpcasestudy.core.ui.utils.viewbinding.viewBinding
import com.mitsinsar.scorpcasestudy.databinding.FragmentPeopleBinding
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PersonItem
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.recycler.PeopleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class PeopleFragment : BaseFragment(R.layout.fragment_people) {

    private val binding by viewBinding(FragmentPeopleBinding::bind)

    private val peopleViewModel by viewModels<PeopleViewModel>()

    private val peopleAdapter = PeopleAdapter()

    private val peopleItemCollector: suspend (List<PersonItem>?) -> Unit = {
        peopleAdapter.submitList(it.orEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        binding.peopleRecyclerView.adapter = peopleAdapter
    }

    private fun initObservers() {
        with(peopleViewModel.peoplePreviewFlow) {
            viewLifecycleOwner.collectLatestOnLifecycle(
                flow = map { it?.peopleItem },
                collector = peopleItemCollector
            )
        }
    }
}
