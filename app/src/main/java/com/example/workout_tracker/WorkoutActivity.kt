package com.example.workout_tracker

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Workout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.activity_workout.*


class WorkoutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        var i=0;
        linear_layout_vertical.gravity = Gravity.CENTER
        val workout = intent.getSerializableExtra("Workout") as Workout
        textViewNomeWorkout.text = workout.nome
        textViewNomeWorkout.paintFlags = textViewNomeWorkout.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        workout.exerciseList.forEach {
                i++
            setExerciseNamelayout(i,it)

                for ( i in 0 until it.serie){
                    setSerieLayout(i)
                }
        }
        setCompleteButton(workout)

    }
    fun btnFinishClick(workout: Workout) {
       /* Log.d(null, "lel")
        var linearLayout = linear_layout_vertical.getChildAt(1) as LinearLayout
        var textView = linearLayout.getChildAt(0) as TextView
        Log.d(null, "${textView.text}")*/
        
       // workout.exerciseList[3].addExecution()

    }
    fun setCompleteButton(workout: Workout){
        val button : MaterialButton = MaterialButton(this,)

        button.text = "Completato"
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        button.gravity = Gravity.CENTER
        button.textSize = 24f
        button.setPadding(30)
        button.setOnClickListener {btnFinishClick(workout) }

        linear_layout_vertical.addView(button)
    }
    fun setExerciseNamelayout(i: Int, exercise: Exercise) {
        var textView: MaterialTextView =  MaterialTextView(this)
        textView.text="$i- ${exercise.nome}"
        textView.textSize = 24f
        textView.setPadding(24)
        textView.typeface = Typeface.DEFAULT_BOLD
        linear_layout_vertical.addView(textView)
    }
    fun setSerieLayout(i :Int){
        var linearLayout : LinearLayout = LinearLayout(this)
        linear_layout_vertical.addView(linearLayout)


        var textView2: MaterialTextView =  MaterialTextView(this,)
        textView2.text="${i+1}° Serie"
        textView2.setPadding(70)
        textView2.textSize = 18f

        var textInputLayout = TextInputLayout(this)
        textInputLayout.layoutParams = LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT)
        textInputLayout.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout.hint = "N°Rep"
        textInputLayout.boxStrokeColor = Color.RED
        textInputLayout

        var editText = TextInputEditText(textInputLayout.context)

        editText.inputType = (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        editText.filters += InputFilter.LengthFilter(4)
        textInputLayout.setPadding(25,0,25,0)
        textInputLayout.addView(editText, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))


        var textInputLayout2 = TextInputLayout(this)
        textInputLayout2.layoutParams = LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT)
        textInputLayout2.boxStrokeColor = Color.RED
        textInputLayout2.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout2.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout2.hint ="Kg"
        var editText2 = TextInputEditText(textInputLayout2.context)
        editText2.inputType = (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL )
        editText2.filters += InputFilter.LengthFilter(6)
        textInputLayout2.setPadding(25,0,25,0)
        textInputLayout2.addView(editText2, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var checkBox :CheckBox = CheckBox(this)
        checkBox.setPadding(70)
        checkBox.setOnClickListener {
            if(checkBox.isChecked){
                linearLayout.setBackgroundColor(Color.GREEN)
            }else{
                linearLayout.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        linearLayout.addView(textView2)
        linearLayout.addView(textInputLayout)
        linearLayout.addView(textInputLayout2)
        linearLayout.addView(checkBox)

    }
}

