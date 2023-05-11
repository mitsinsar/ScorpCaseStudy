package com.mitsinsar.scorpcasestudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mitsinsar.scorpcasestudy.core.ui.utils.viewbinding.viewBinding
import com.mitsinsar.scorpcasestudy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
