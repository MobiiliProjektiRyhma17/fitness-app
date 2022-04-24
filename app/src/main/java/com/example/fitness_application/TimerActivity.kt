package com.example.fitness_application

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.browser.browseractions.BrowserActionItem
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.fitness_application.databinding.ActivityTimerBinding
import kotlin.math.roundToInt

class TimerActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityTimerBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        actionBar = supportActionBar!!
        actionBar.title = "Sekuntikello"

        binding.startStopButton.setOnClickListener { startStopTimer() }
        binding.resetButton.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }
    private fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()

    }
    private fun resetTimer()
    {
        stopTimer()
        time = 0.0
        binding.timeTV.text = getTimeStringFromDouble(time)

    }

    private fun startTimer()
    {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.startStopButton.text = "Stop"
        binding.startStopButton.icon =getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true

    }

    private fun stopTimer()
    {
        stopService(serviceIntent)
        binding.startStopButton.text = "Start"
        binding.startStopButton.icon =getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA,0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String
    {
        val resultsInt = time.roundToInt()
        val hours = resultsInt % 86400 / 3600
        val minutes = resultsInt % 86400 % 3600 / 60
        val seconds = resultsInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)

    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min ,sec)




}
