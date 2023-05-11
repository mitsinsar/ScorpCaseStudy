package com.mitsinsar.scorpcasestudy.core.ui.utils.viewbinding

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

fun <T : ViewBinding> AppCompatActivity.viewBinding(bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }
