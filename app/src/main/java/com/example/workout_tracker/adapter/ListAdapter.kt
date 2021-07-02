package com.example.workout_tracker.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.*
import com.example.workout_tracker.R
import com.example.workout_tracker.WorkoutActivity
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_view_item.*


class ListAdapter(private val context: Context?, private val data: ArrayList<Workout>) : BaseAdapter() {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser = mAuth.currentUser
    val idUser = currentUser?.uid
    var mUserReference = FirebaseDatabase.getInstance().getReference("users")
    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {


        var newView = convertView
        newView = null
        if (newView == null) {
            newView = LayoutInflater.from(context).inflate(R.layout.list_view_item, null)
            Log.d("workoout", "lol")
        }
        if (newView != null) {

            val txtNomeWorkout: TextView = newView.findViewById(R.id.txtNomeWorkout)
            // val txtLastDate: TextView= newView.findViewById(R.id.txtLastDate)
            //txtLastDate.text = data[position].lastRun.toString()
            txtNomeWorkout.text = data[position].nome
        }

        fillWorkout(position, newView)

        val btn: ImageButton = newView!!.findViewById(R.id.imageButton2)
        val btn2: ImageButton = newView!!.findViewById(R.id.imageButton3)
        btn.setOnClickListener {
            Log.d(null, "bottone avvio $position")
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.putExtra("Workout", data[position])
            context!!.startActivity(intent)

        }
        btn2.setOnClickListener {
            AlertDialog.Builder(context)
                    .setTitle(context!!.getString(R.string.vuoi_uscire))
                    .setMessage(context.getString(R.string.allenamento_verra_cancellato))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { arg0, arg1 ->
                        mUserReference.child(idUser!!).child(data[position].nome).removeValue()
                        FirebaseDatabase.getInstance().getReference("$idUser+${data[position].nome}").removeValue()
                    }.create().show()


        }





        return newView
    }

    private fun fillWorkout(position: Int, newView: View?) {
        val linearLayout: LinearLayout = newView!!.findViewById(R.id.linear_layout)

        data[position].exerciseList.forEach { exe ->
            var exercise: Exercise = exe
            var textView: TextView = TextView(this.context)
            textView.text = "${exercise.serie} x ${exercise.ripetizioni} ${exercise.nome}"
            textView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            Log.d("workoout", textView.text.toString())
            linearLayout.addView(textView)

        }
    }
}
