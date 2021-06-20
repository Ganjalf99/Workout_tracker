package com.example.workout_tracker.fragment.exercise


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workout_tracker.R
import com.example.workout_tracker.util.Execution
import com.example.workout_tracker.util.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_statistics.*

class StatisticsFragment: Fragment(R.layout.fragment_statistics){
    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mAuth.currentUser
    val idUser = currentUser?.uid
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false);    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showWorkoutStatistics(workout: Workout) {
        textViewInfo.text = workout.nome
        var str =""
        var mUserReference = FirebaseDatabase.getInstance().getReference("$idUser+${workout.nome}")

        workout.exerciseList.forEach {

            mUserReference.child(it.nome).get().addOnSuccessListener {
                it.key?.let { it1 -> Log.d(null, it1)
                    str += "\n${it1.toUpperCase()} \n"
                }
             it.children.forEach {
                 val exe =  it.getValue(Execution::class.java)
                 str += "${exe.toString()} \n"
                Log.d(null, exe.toString() )
             }
                textViewstats.text = str
            }
        }
    }
}