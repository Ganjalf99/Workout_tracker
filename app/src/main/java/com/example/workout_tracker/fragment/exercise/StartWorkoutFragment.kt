package com.example.workout_tracker.fragment.exercise


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.example.workout_tracker.R
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout

class StartWorkoutFragment: Fragment(R.layout.fragment_startworkout){

    override fun onCreateView(
            inflater:   LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}