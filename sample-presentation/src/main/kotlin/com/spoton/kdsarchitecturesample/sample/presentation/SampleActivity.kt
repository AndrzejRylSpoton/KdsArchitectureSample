package com.spoton.kdsarchitecturesample.sample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spoton.kdsarchitecturesample.sample.presentation.databinding.ActivitySampleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}