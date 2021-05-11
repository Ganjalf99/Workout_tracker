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
        var workout = Workout("Petto/bicipiti")
        var es1 = Exercise("pancaPiana,",3,10,120)
        var es2 = Exercise("curlbilli,",3,12,120)
        var es3 = Exercise("pancaPiana,",3,4,120)
        workout.addExercise(es1)
        Log.d("WorkoutFragment",workout.toString())
        workout.addExercise(es2)
        Log.d("WorkoutFragment",workout.toString())
        workout.removeExercise(es1)
        Log.d("WorkoutFragment",workout.toString())
    }
}