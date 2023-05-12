package com.mitsinsar.scorpcasestudy.peoplelisting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitsinsar.scorpcasestudy.core.ui.utils.coroutine.SingleActiveJobHandler
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.model.PeoplePreview
import com.mitsinsar.scorpcasestudy.peoplelisting.ui.usecase.PeoplePreviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peoplePreviewUseCase: PeoplePreviewUseCase
) : ViewModel() {

    private val _peoplePreviewFlow = MutableStateFlow<PeoplePreview?>(null)
    val peoplePreviewFlow: StateFlow<PeoplePreview?>
        get() = _peoplePreviewFlow

    private var previewInitializationJobHandler = SingleActiveJobHandler()
    private var loadMorePeopleJobHandler = SingleActiveJobHandler()

    init {
        initInitialPreviewState()
    }

    fun onInfoBarActionClick() {
        _peoplePreviewFlow.value?.let { currentPreview ->
            viewModelScope.launch {
                peoplePreviewUseCase.getUpdatedPreviewWithInfoAction(currentPreview).collectLatest { newPreview ->
                    _peoplePreviewFlow.value = newPreview
                }
            }
        }
    }

    fun onSwipeToRefresh() {
        previewInitializationJobHandler.runIfJobIsNotActive {
            viewModelScope.launch {
                peoplePreviewUseCase.refreshPeopleList(_peoplePreviewFlow.value).collectLatest { newPreview ->
                    _peoplePreviewFlow.value = newPreview
                }
            }
        }
    }

    fun onRecyclerItemsInserted(isLastItemCompletelyVisible: Boolean) {
        _peoplePreviewFlow.value?.let { currentPreview ->
            loadMorePeopleJobHandler.runIfJobIsNotActive {
                viewModelScope.launch {
                    peoplePreviewUseCase.loadMoreIfListedItemsAreSmallerThanScreen(
                        isLastItemCompletelyVisible,
                        currentPreview
                    )?.collectLatest { newPreview ->
                        _peoplePreviewFlow.value = newPreview
                    }
                }
            }
        }
    }

    fun onRecyclerBottomItemCountThreshold() {
        _peoplePreviewFlow.value?.let { currentPreview ->
            loadMorePeopleJobHandler.runIfJobIsNotActive {
                viewModelScope.launch {
                    peoplePreviewUseCase.loadMorePeople(currentPreview).collectLatest { newPreview ->
                        _peoplePreviewFlow.value = newPreview
                    }
                }
            }
        }
    }

    private fun initInitialPreviewState() {
        previewInitializationJobHandler.runIfJobIsNotActive {
            viewModelScope.launch {
                peoplePreviewUseCase.getInitialPreview().collectLatest { newPreview ->
                    _peoplePreviewFlow.value = newPreview
                }
            }
        }
    }
}
