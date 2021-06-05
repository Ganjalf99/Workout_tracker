package com.example.workout_tracker.util
import com.example.workout_tracker.Exceptions.ExerciseAlreadyInListException
import java.io.Serializable
open class BaseExercise( var nome: String) : Serializable{

}

class Exercise(nome : String,  var serie: Int,  val ripetizioni: Int, val recupero: Int):BaseExercise(nome) {
    var executionList = mutableListOf<Execution>()
    override fun toString(): String {
        return "$nome $serie x $ripetizioni, recupero=$recupero"
    }
    fun addExecution(execution :Execution) :Boolean{
            return executionList.add(execution )

    }
    fun removeExercise(execution :Execution):Boolean{
        return executionList.remove(execution)
    }
}
 class TimerExercise(  nome : String, timer : Int):BaseExercise(nome) {
    var timer = timer
     override fun toString(): String {
         return "$nome,timer=$timer"
     }
 }
