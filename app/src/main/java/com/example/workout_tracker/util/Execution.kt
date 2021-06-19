package com.example.workout_tracker.util

import java.io.Serializable
import java.util.*

class Execution(val date : Tempo, val nSerie :Int,  val nRep :Int, val Peso :Int): Serializable {

    override fun toString(): String {
        return "$date : $nSerie° Serie N°Rep=$nRep, Kg=$Peso"
    }
}

class Tempo (val giorno :Int ,val mese :Int ,val anno :Int){

    override fun toString(): String {
        return "$giorno/$mese/$anno"
    }
}
