package com.example.workout_tracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.workout_tracker.fragment.exercise.ExerciseProfileFragment
import com.google.firebase.auth.FirebaseAuth

class TransitionActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transition_activty)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser


        Handler(Looper.getMainLooper()).postDelayed({
            if(user != null){
                val dashboardIntent = Intent(this, MainActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            }else{
                val signInIntent = Intent(this, LoginActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 2000)

    }
}