package com.example.workout_tracker

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.style.TtsSpan
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.example.workout_tracker.Exceptions.MissValueException
import com.example.workout_tracker.Services.Timer
import com.example.workout_tracker.util.Execution
import com.example.workout_tracker.util.Exercise
import com.example.workout_tracker.util.Tempo
import com.example.workout_tracker.util.Workout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_workout.*
import java.time.LocalDateTime
import java.time.temporal.Temporal
import java.util.*


class WorkoutActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mAuth.currentUser
    val idUser = currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        registerReceiver(uiUpdated, IntentFilter("TIMER_UPDATED"));
        startButton.setOnClickListener { onStartBtnClick() }
        stopButton.setOnClickListener { onStopBtnClick() }
        var i = 0;
        linear_layout_vertical.gravity = Gravity.CENTER
        val workout = intent.getSerializableExtra("Workout") as Workout
        textViewNomeWorkout.text = workout.nome
        textViewNomeWorkout.paintFlags = textViewNomeWorkout.paintFlags
        workout.exerciseList.forEach {
            i++
            setExerciseNamelayout(i, it)

            for (i in 0 until it.serie) {
                setSerieLayout(i)
            }
        }
        setCompleteButton(workout)

    }


    fun btnFinishClick(workout: Workout) {
        var i = 1
        var mUserReference = FirebaseDatabase.getInstance().getReference("$idUser+${workout.nome}")

        workout.exerciseList.forEach {
            i++

            for (j in 0 until it.serie) {
                var linearLayout = linear_layout_vertical.getChildAt(i) as LinearLayout
                val textInputRep = linearLayout.getChildAt(1) as TextInputLayout
                val textInputKg = linearLayout.getChildAt(2) as TextInputLayout

                val nRep = textInputRep.editText!!.text.toString()
                val kg = textInputKg.editText!!.text.toString()
                if ((nRep == "") or (kg == "")) {
                    Log.d(null, "esercizio non completo")
                } else {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val time = Tempo(day, month + 1, year)
                    val exe = Execution(time, j + 1, nRep.toInt(), kg.toInt())
                    Log.d(null, exe.toString())
                    mUserReference.child(it.nome).push().setValue(exe)
                }
                i++
            }


        }
        Toast.makeText(this, getString(R.string.allenamento_salvato), Toast.LENGTH_SHORT).show()
        finish()

    }


    fun setCompleteButton(workout: Workout) {
        val button: MaterialButton = MaterialButton(this)

        button.text = getString(R.string.continua)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        button.layoutParams = params
        button.gravity = Gravity.CENTER
        button.textSize = 24f
        button.setPadding(50)
        button.setOnClickListener { btnFinishClick(workout) }

        linear_layout_vertical.addView(button)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setExerciseNamelayout(i: Int, exercise: Exercise) {
        var textView: MaterialTextView = MaterialTextView(this)
        textView.text = " ${exercise.nome}"
        textView.textSize = 24f
        textView.setPadding(24)

        textView.setTextColor(resources.getColor(R.color.purple_200, null))
        textView.typeface = Typeface.DEFAULT_BOLD
        linear_layout_vertical.addView(textView)
    }

    fun setSerieLayout(i: Int) {
        var linearLayout: LinearLayout = LinearLayout(this)
        linear_layout_vertical.addView(linearLayout)


        var textView2: MaterialTextView = MaterialTextView(this)
        textView2.text = "${i + 1}° ${getString(R.string.serie)}"
        textView2.setPadding(70)
        textView2.textSize = 18f

        var textInputLayout = TextInputLayout(this)
        textInputLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout.boxBackgroundColor = Color.TRANSPARENT

        textInputLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout.hint = getString(R.string.n_rep)
        textInputLayout.boxStrokeColor = Color.RED
        textInputLayout

        var editText = TextInputEditText(textInputLayout.context)

        editText.inputType = (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        editText.filters += InputFilter.LengthFilter(4)
        textInputLayout.setPadding(50, 0, 50, 0)
        textInputLayout.addView(editText, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))


        var textInputLayout2 = TextInputLayout(this)
        textInputLayout2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout2.boxStrokeColor = Color.RED
        textInputLayout2.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout2.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout2.hint = getString(R.string.kh)
        var editText2 = TextInputEditText(textInputLayout2.context)
        editText2.inputType = (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        editText2.filters += InputFilter.LengthFilter(6)

        textInputLayout2.setPadding(50, 0, 50, 0)
        textInputLayout2.addView(editText2, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var checkBox: CheckBox = CheckBox(this)
        checkBox.setPadding(50, 0, 0, 0)
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                linearLayout.setBackgroundColor(Color.GREEN)
            } else {
                linearLayout.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        linearLayout.addView(textView2)
        linearLayout.addView(textInputLayout)
        linearLayout.addView(textInputLayout2)
        linearLayout.addView(checkBox)

    }

    private var uiUpdated = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //sistemare tempo
            var time = intent!!.getIntExtra("timeS", 0)
            Log.d(null, "$time")
            textViewTimer.text = "${time / 60}:${time % 60}"
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(uiUpdated);
    }

    private fun onStartBtnClick() {
        val time = textnumes.editText!!.text.toString()
        val textViewTimer = textViewTimer.text.toString()
        val intent = Intent(this, Timer::class.java)
        if (textViewTimer == "0:0") {
            if (time != "") {

                intent.putExtra("time", time.toInt())
                startService(intent)
            }
        }

    }

    private fun onStopBtnClick() {
        val intent = Intent(this, Timer::class.java)
        stopService(intent)

        textViewTimer.text = "0:0"
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setTitle(R.string.vuoi_uscire)
                .setMessage(getString(R.string.progressi_persi))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { arg0, arg1 ->

                    super.onBackPressed()
                }
                .create().show()
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val ret = super.dispatchTouchEvent(ev)
        ev?.let { event ->
            if (event.action == MotionEvent.ACTION_UP) {
                currentFocus?.let { view ->
                    if (view is EditText) {
                        val touchCoordinates = IntArray(2)
                        view.getLocationOnScreen(touchCoordinates)
                        val x: Float = event.rawX + view.getLeft() - touchCoordinates[0]
                        val y: Float = event.rawY + view.getTop() - touchCoordinates[1]
                        //If the touch position is outside the EditText then we hide the keyboard
                        if (x < view.getLeft() || x >= view.getRight() || y < view.getTop() || y > view.getBottom()) {
                            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                            view.clearFocus()
                        }
                    }
                }
            }
        }
        return ret
    }
}

