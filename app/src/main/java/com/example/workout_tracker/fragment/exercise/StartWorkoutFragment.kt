package com.example.workout_tracker.fragment.exercise


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.workout_tracker.Exceptions.ExerciseAlreadyInListException
import com.example.workout_tracker.R
import com.example.workout_tracker.WorkoutActivity
import com.example.workout_tracker.adapter.ListAdapter
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import kotlinx.android.synthetic.main.fragment_startworkout.*
import kotlinx.android.synthetic.main.list_view_item.*


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
        var workoutList = mutableListOf<Workout>()
        var workout = Workout("Petto/bicipiti")
        var workout2 = Workout("schiena/tricipiti")
        var es1 = Exercise("squat,",2,10,120)
        var es2 = Exercise("curlbilli,",3,12,120)
        var es3 = Exercise("pancaPiana,",5,4,120)
        var es4 = Exercise("squasat,",2,10,120)
        var es5 = Exercise("squsdsat,",2,10,120)
        try {
            workout.addExercise(es2)
            workout.addExercise(es1)
            workout.addExercise(es4)
            workout.addExercise(es5)
            workout.addExercise(es3)
            workout2.addExercise(es3)
            workout2.addExercise(es2)

            workoutList.add(workout)
            workoutList.add(workout2)
        }catch (e  : ExerciseAlreadyInListException){
            Toast.makeText(this.context,"esercizio  gia presente in scheda",Toast.LENGTH_SHORT).show()
        }

        list_view_workouts.adapter= ListAdapter(this.context,workoutList)
        list_view_workouts.setOnItemClickListener ( object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /*creare activity modifica*/

                Log.d(null,"$position")
                parent!!.getItemAtPosition(position)
                var workout= parent!!.getItemAtPosition(position) as Workout
                Log.d(null,"${workout.nome}")

            }

        } )

    }

}