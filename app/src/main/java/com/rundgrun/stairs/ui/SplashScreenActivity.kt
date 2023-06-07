package com.rundgrun.stairs.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class SplashScreenActivity : AppCompatActivity() {

    private var job: Job? = null

    override fun onStart() {
        super.onStart()
        job = lifecycleScope.async {
            delay(2000)
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
}