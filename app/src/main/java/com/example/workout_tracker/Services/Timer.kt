package com.example.workout_tracker.Services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import com.example.workout_tracker.R


class Timer : Service() {
    private lateinit var countDownTimer: CountDownTimer
    private var time = 0

    private lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.countdown5)
        mediaPlayer.setLooping(false)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        time = intent!!.getIntExtra("time", 0)

        countDownTimer = object : CountDownTimer(time.toLong() * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val i = Intent("TIMER_UPDATED")
                time -= 1
                Log.d(null, "$time")
                if (time == 5) {
                    mediaPlayer.start()
                }
                i.putExtra("timeS", time)

                sendBroadcast(i)
            }

            override fun onFinish() {

            }


        }
        countDownTimer.start()


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer.stop()
        countDownTimer.cancel()
        super.onDestroy()

    }
}