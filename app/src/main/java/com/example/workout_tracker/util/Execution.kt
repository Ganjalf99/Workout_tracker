package com.example.workout_tracker.util

import java.io.Serializable
import java.util.*

class Execution(val date : Tempo? = null, val nserie :Int =0,  val nrep :Int=0, val peso :Int=0): Serializable {



    override fun toString(): String {
        return "$date : $nserie° Serie N°Rep=$nrep, Kg=$peso"
    }
}

class Tempo (val giorno :Int=0 ,val mese :Int=0 ,val anno :Int=0){

    override fun toString(): String {
        return "$giorno/$mese/$anno"
    }
}
