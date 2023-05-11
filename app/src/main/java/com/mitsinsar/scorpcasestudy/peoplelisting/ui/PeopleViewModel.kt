package com.mitsinsar.scorpcasestudy.peoplelisting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        initInitialPreviewState()
    }

    private fun initInitialPreviewState() {
        viewModelScope.launch {
            peoplePreviewUseCase.getInitialPreview().collectLatest { newPreview ->
                _peoplePreviewFlow.value = newPreview
            }
        }
    }
}
