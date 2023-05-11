package com.mitsinsar.scorpcasestudy.core.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes val layoutResId: Int) : Fragment(layoutResId)
