package com.example.workout_tracker.fragment.exercise


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workout_tracker.R
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import kotlinx.android.synthetic.main.fragment_newtraining.*
import kotlinx.android.synthetic.main.fragment_newtraining2.*
import kotlinx.android.synthetic.main.fragment_startworkout.*

class NewTrainingFragment2: Fragment(R.layout.fragment_newtraining2){

    override fun onCreateView(inflater:   LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = arguments
        if(bundle != null){
            var nomeWorkout = bundle!!.get("nomeWorkout")
            var numeroEsercizi = bundle!!.get("numeroEsercizi")
            textView2.text = nomeWorkout as String
        }



    }




}