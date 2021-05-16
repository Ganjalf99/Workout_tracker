package com.example.workout_tracker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams;
import android.widget.*
import com.example.workout_tracker.R
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import kotlinx.android.synthetic.main.list_view_item.*


class ListAdapter(private val context: Context?, private val data: MutableList<Workout>): BaseAdapter() {
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {


        var newView = convertView
        if( newView== null){
            newView = LayoutInflater.from(context).inflate(R.layout.list_view_item,null)
        }
        if(newView != null){

            val txtNomeWorkout: TextView= newView.findViewById(R.id.txtNomeWorkout)

            txtNomeWorkout.text =data[position].nome
        }

        fillWorkout(position,newView)


        val btn : ImageButton = newView!!.findViewById(R.id.imageButton2)
        val btn2 : ImageButton = newView!!.findViewById(R.id.imageButton3)
        btn.setOnClickListener { Log.d(null,"bottone avvio"/*creare activyty avvio*/) }
        btn2.setOnClickListener { Log.d(null,"bottone stop") }


        return newView
    }
    private fun fillWorkout( position: Int, newView : View?){
        val  linearLayout :LinearLayout =  newView!!.findViewById(R.id.linear_layout)

        data[position].exerciseList.forEach { exe ->
            var exercise: Exercise = exe
            var textView: TextView = TextView(this.context)
            textView.text = "${exercise.serie} x ${exercise.ripetizioni} ${exercise.nome}"
            textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            linearLayout.addView(textView)
        }
    }
}
