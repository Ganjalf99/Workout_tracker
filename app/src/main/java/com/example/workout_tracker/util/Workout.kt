package com.example.workout_tracker.util

import com.example.workout_tracker.Exceptions.ExerciseAlreadyInListException
import java.lang.Exception

class Workout(var nome: String){
    var exerciseList = mutableListOf<Exercise>()


    fun addExercise(exercise: Exercise) :Boolean{
       if( exerciseList.find { elem -> elem.nome== exercise.nome}==null){

           return exerciseList.add(exercise )
       }
        throw ExerciseAlreadyInListException("Esercizio già presente nella scheda")
        return false
    }
    fun removeExercise(exercise: Exercise):Boolean{
           return exerciseList.remove(exercise)
    }

    override fun toString(): String {
        var string :String = "$nome \n"
        exerciseList.forEach{elem -> string =  string.plus(elem.toString()+"\n")}
        return string
    }
}
