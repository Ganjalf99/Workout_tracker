package com.example.workout_tracker.util

class Workout(var nome: String){
    var exerciseList = mutableListOf<BaseExercise>()
    var eserciseNumber = exerciseList.size

    fun addExercise(exercise: Exercise) :Boolean{
       if( exerciseList.find { elem -> elem.nome== exercise.nome}==null){
           return exerciseList.add(exercise )
       }
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
