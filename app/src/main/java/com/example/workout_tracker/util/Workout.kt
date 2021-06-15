package com.example.workout_tracker.util

import android.provider.ContactsContract
import com.example.workout_tracker.Exceptions.ExerciseAlreadyInListException
import com.example.workout_tracker.R
import java.io.Serializable
import java.lang.Exception
import java.time.LocalDate.now
import java.util.*

class Workout(var nome: String ) : Serializable{
   var exerciseList = mutableListOf<Exercise>()



    fun addExercise(exercise: Exercise) :Boolean{
       if( exerciseList.find { elem -> elem.nome== exercise.nome}==null){

           return exerciseList.add(exercise )
       }
        throw ExerciseAlreadyInListException("Esercizio già presente")

    }
    fun removeExercise(exercise: Exercise):Boolean{
           return exerciseList.remove(exercise)
    }

    override fun toString(): String {
        var string :String = "$nome \n "
        exerciseList.forEach{elem -> string =  string.plus(elem.toString()+"\n")}
        return string
    }
}
