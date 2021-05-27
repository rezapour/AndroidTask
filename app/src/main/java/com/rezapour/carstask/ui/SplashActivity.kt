package com.rezapour.carstask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rezapour.carstask.R
import java.util.*


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withTimerTask(2000)
    }

    private fun withTimerTask(delay: Long) {
        val timerTask = object : TimerTask() {
            override fun run() {
                nextActivity()
            }
        }

        val timer = Timer()
        timer.schedule(timerTask, delay)
    }

    private fun nextActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }
}

