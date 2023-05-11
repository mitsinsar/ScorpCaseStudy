package com.mitsinsar.scorpcasestudy.core.ui.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectLatestOnLifecycle(
    flow: Flow<T>?,
    collector: suspend (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.RESUMED
) {
    lifecycleScope.launch {
        flow?.flowWithLifecycle(lifecycle, state)?.collectLatest(collector)
    }
}
