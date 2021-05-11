package com.example.workout_tracker.util
open class BaseExercise( var nome: String){

}

class Exercise(nome : String,  var serie: Int,  val ripetizioni: Int, val recupero: Int):BaseExercise(nome) {
    override fun toString(): String {
        return "$nome $serie x $ripetizioni, recupero=$recupero"
    }
}
 class TimerExercise(  nome : String, timer : Int):BaseExercise(nome) {
    var timer = timer
     override fun toString(): String {
         return "$nome,timer=$timer"
     }
 }
