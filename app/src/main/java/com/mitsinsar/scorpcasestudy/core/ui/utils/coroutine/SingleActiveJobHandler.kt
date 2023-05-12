package com.mitsinsar.scorpcasestudy.core.ui.utils.coroutine

import kotlinx.coroutines.Job

class SingleActiveJobHandler {

    private var job: Job? = null

    fun runIfJobIsNotActive(action: () -> Job?) {
        if (job?.isActive == true) {
            return
        }
        job = action()
    }
}
