package com.example.workout_tracker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.workout_tracker.fragment.SettingsFragment
import com.example.workout_tracker.fragment.exercise.ExerciseProfileFragment
import com.example.workout_tracker.fragment.exercise.NewTrainingFragment
import com.example.workout_tracker.fragment.exercise.StartWorkoutFragment
import com.example.workout_tracker.fragment.exercise.StatisticsFragment
import com.example.workout_tracker.fragment.food.AddFoodFragment
import com.example.workout_tracker.fragment.food.FoodProfilefragment
import com.example.workout_tracker.fragment.food.MacroChartFragment
import com.example.workout_tracker.util.Workout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_workout.*

class WorkoutActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        val workout = intent.getSerializableExtra("Workout") as Workout
        textViewNomeWorkout.text = workout.nome
            workout.exerciseList.forEach {
                var textView: TextView = TextView(this)
                textView.text="${it.nome}"
                linear_layout_vertical.addView(textView)


                for ( i in 0 until it.serie){
                    var linearLayout : LinearLayout = LinearLayout(this)
                    linear_layout_vertical.addView(linearLayout)
                    var textView2: TextView = TextView(this)
                    var edittxtserie: EditText = EditText(this)
                    edittxtserie.hint = "N° rep"
                    edittxtserie.inputType = InputType.TYPE_CLASS_NUMBER
                    var edittxtpeso: EditText = EditText(this)
                    edittxtpeso.hint = "Kg"
                    edittxtpeso.inputType = InputType.TYPE_CLASS_NUMBER
                    textView2.text="${i+1}° Serie"
                    linearLayout.addView(textView2)
                    linearLayout.addView(edittxtserie)
                    linearLayout.addView(edittxtpeso)
                }



            }
    }
    fun btnFinishClick(v : View) {
        Log.d(null,"lel")
        var linearLayout = linear_layout_vertical.getChildAt(1) as LinearLayout
        var textView = linearLayout.getChildAt(0) as TextView
        Log.d(null,"${textView.text}")
    }
}

