package com.example.workout_tracker.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.view.ViewGroup.LayoutParams;
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

        var i =0
        var newView = convertView
        if( newView== null){
            newView = LayoutInflater.from(context).inflate(R.layout.list_view_item,null)
        }
        val  linearLayout :LinearLayout =  newView!!.findViewById(R.id.linear_layout)
        if(newView != null){

            val txtNomeWorkout: TextView= newView.findViewById(R.id.txtNomeWorkout)

            txtNomeWorkout.text =data[position].nome
        }

        data[position].exerciseList.forEach{ exe->
            var exercise:Exercise = exe
            var textView : TextView = TextView(this.context)
            textView.text ="${exercise.serie} x ${exercise.ripetizioni} ${exercise.nome}"
            textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
            linearLayout.addView(textView)

        }



        return newView
    }
}