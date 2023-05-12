package com.mitsinsar.scorpcasestudy.peoplelisting.ui.model

import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.mitsinsar.scorpcasestudy.R

sealed class PeoplePreviewInfoBarAction {

    @Duration
    open val visibilityLength: Int = BaseTransientBottomBar.LENGTH_INDEFINITE

    @get:StringRes
    abstract val buttonResId: Int

    @get:StringRes
    abstract val infoText: Int

    object NoUserFoundInfoBar : PeoplePreviewInfoBarAction() {
        override val buttonResId: Int
            get() = R.string.refresh
        override val infoText: Int
            get() = R.string.no_user_found
    }

    object GenericErrorBar : PeoplePreviewInfoBarAction() {
        override val buttonResId: Int
            get() = R.string.try_again
        override val infoText: Int
            get() = R.string.an_error_occurred
    }

    object ServerErrorBar : PeoplePreviewInfoBarAction() {
        override val buttonResId: Int
            get() = R.string.try_again
        override val infoText: Int
            get() = R.string.server_error
    }
}
