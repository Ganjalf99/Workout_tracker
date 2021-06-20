package com.example.workout_tracker.fragment.exercise


import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.workout_tracker.Exceptions.ExerciseAlreadyInListException
import com.example.workout_tracker.MainActivity
import com.example.workout_tracker.R
import com.example.workout_tracker.adapter.ListAdapter
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_exerciseprofile.*
import kotlinx.android.synthetic.main.fragment_startworkout.*
import kotlinx.android.synthetic.main.list_view_item.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class StartWorkoutFragment: Fragment(R.layout.fragment_startworkout){

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

        super.onViewCreated(view, savedInstanceState)
        val currentUser = mAuth.currentUser
        val idUser = currentUser?.uid

        var mUserReference = FirebaseDatabase.getInstance().getReference("users")


        var workoutList = ArrayList<Workout>()
        var listAdapter =  ListAdapter(this.context, workoutList)
        list_view_workouts.adapter= listAdapter
        mUserReference.child(idUser!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                workoutList.clear()
                snapshot.children.forEach { workoutList.add(createWorkoutFromList(it) )  }

                listAdapter.notifyDataSetChanged()




            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("StartWorkloutFragment", "Failed to read value.", error.toException());
            }

        })








        list_view_workouts.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /* ti porta a statistichs con la scheda selezionata*/

                Log.d(null, "$position")
                parent!!.getItemAtPosition(position)
                var workout = parent!!.getItemAtPosition(position) as Workout
                val act =activity as MainActivity
                act.showWorkoutStatistics(workout)
                Log.d(null, "${workout.nome}")

            }

        })

    }

    private fun createWorkoutFromList(snapshot: DataSnapshot) : Workout {
        var hashMap  = snapshot.value as HashMap<*, *>
        var list = hashMap.values.toList()
        var workout : Workout = Workout(list[1].toString())
        var listOfExercise = list[0]  as ArrayList<*>


        listOfExercise.forEach {

            val map = it as HashMap<*,*>
            val list = it.values.toList()
            val nome = list[2] as String
            val serie = list[1] as Long
            val recupero = list[0] as Long
            val ripetizioni = list [3] as Long
            val exercise = Exercise(nome,serie.toInt(),ripetizioni.toInt(),recupero.toInt())
            workout.addExercise(exercise)
             }

        return workout
    }

}