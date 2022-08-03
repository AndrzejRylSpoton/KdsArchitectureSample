package com.spoton.kdsarchitecturesample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Run activity from other module
        startActivity(Intent("com.spoton.sample.START_SAMPLE"))

        finish()
    }
}
