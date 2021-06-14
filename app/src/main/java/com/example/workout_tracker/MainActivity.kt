package com.example.workout_tracker

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.setPadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.workout_tracker.fragment.SettingsFragment
import com.example.workout_tracker.fragment.exercise.*
import com.example.workout_tracker.fragment.food.AddFoodFragment
import com.example.workout_tracker.fragment.food.FoodProfilefragment
import com.example.workout_tracker.fragment.food.MacroChartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_workout.*
import kotlinx.android.synthetic.main.fragment_addfood.*
import kotlinx.android.synthetic.main.fragment_newtraining.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //val drawerListener = DrawerListener()
        //val bottomlistener = BottomListener()
        //all 'avvio dell'app mi posiziono sullo start workout
        bottom_nav.selectedItemId = R.id.new_workout
        supportFragmentManager.commit { setReorderingAllowed(true)
            replace<NewTrainingFragment>(R.id.main_fragment)
        }
        //listener per il drawer menu
        nav_view.setNavigationItemSelectedListener(object  : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                bottom_nav.menu.clear()
                when(item.itemId){
                    R.id.nav_exercise -> {
                        changeMenu(R.menu.bottom_drawer_menu_exercise, R.id.start_workout)
                    }//seleziona l'item di default
                    R.id.nav_food -> {
                        changeMenu(R.menu.bottom_drawer_menu_food, R.id.add_food)
                    }
                    R.id.nav_settings -> {
                        bottom_nav.visibility = View.GONE//nascone il bottom menu
                        supportFragmentManager.commit { setReorderingAllowed(true)
                            replace<SettingsFragment>(R.id.main_fragment)
                        }
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                }
                return true
            }

        })
        //listener per i bottom menu
        bottom_nav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                //cambio il fragment inbase alla icona clicata
                when(item.itemId){
                    R.id.statistics ->  supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<StatisticsFragment>(R.id.main_fragment)
                    }
                    R.id.new_workout ->supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<NewTrainingFragment>(R.id.main_fragment)
                    }
                    R.id.start_workout-> supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<StartWorkoutFragment>(R.id.main_fragment)
                    }
                    R.id.profile_exercise -> supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<ExerciseProfileFragment>(R.id.main_fragment)
                    }
                    R.id.macro_chart ->  supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<MacroChartFragment>(R.id.main_fragment)
                    }
                    R.id.add_food->  supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<AddFoodFragment>(R.id.main_fragment)
                    }
                    R.id.profile_food -> supportFragmentManager.commit { setReorderingAllowed(true)
                        replace<FoodProfilefragment>(R.id.main_fragment)
                    }



                }
                return true
            }


        })


    }
    private fun changeMenu(menu : Int,defoultItem:Int){
        bottom_nav.inflateMenu(menu)
        bottom_nav.visibility=View.VISIBLE
        drawer_layout.closeDrawer((GravityCompat.START))
        bottom_nav.selectedItemId = defoultItem
    }


    //se fa indietro ma è aperto il drawer chiude il drower non l'activity
    override  fun  onBackPressed(){
        if(drawer_layout.isDrawerOpen(GravityCompat.START)) {

            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }


    }

    fun btnContinua( v:View){
        val txtNomeWorkout = txt_nome_workout.text.toString()
        val numeroEsercizi  = txt_numero_esercizi.text.toString()

        if (txtNomeWorkout == ""){
            txt_nome_workout.error = getString(R.string.inserisci_un_valore)
        }else if (numeroEsercizi == ""){
            txt_numero_esercizi.error =  getString(R.string.inserisci_un_valore)
        }else{
            Log.d(null,txtNomeWorkout)
            Log.d(null,numeroEsercizi.toString())
            txt_inserisci_esercizi.text = getString(R.string.inserisci_esercizi)
            btn_continua.visibility = View.INVISIBLE

            for(i in 0..numeroEsercizi.toInt()){
                setSerieLayout(i)
            }
            val button : MaterialButton = MaterialButton(this,)

            button.text = getString(R.string.fatto)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            button.layoutParams = params
           button.gravity = Gravity.CENTER_HORIZONTAL
            button.textSize = 24f
            button.setPadding(50)
           // button.setOnClickListener {  }

            linear_layout_add_exercise.addView(button)
        }


    }
    fun setSerieLayout(i :Int){
        var firstLinearLayout : LinearLayout = LinearLayout(this)
        var linearLayout : LinearLayout = LinearLayout(this)
        linear_layout_add_exercise.addView(firstLinearLayout)
        linear_layout_add_exercise.addView(linearLayout)


        var textView2: MaterialTextView =  MaterialTextView(this,)
        textView2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, )
        textView2.text="${i+1}° Esercizio"

        textView2.gravity = Gravity.CENTER
        textView2.textSize = 18f
        textView2.typeface = Typeface.DEFAULT_BOLD

        var textInputLayout = TextInputLayout(this)
        textInputLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)
        textInputLayout.boxBackgroundColor = Color.TRANSPARENT

        textInputLayout.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout.hint = "N°Serie"
        textInputLayout.boxStrokeColor = Color.RED
        textInputLayout

        var editText = TextInputEditText(textInputLayout.context)

        editText.inputType = (InputType.TYPE_CLASS_NUMBER )
        editText.filters += InputFilter.LengthFilter(2)
        textInputLayout.setPadding(25,0,25,0)
        textInputLayout.addView(editText, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))


        var textInputLayout2 = TextInputLayout(this)
        textInputLayout2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1F)
        textInputLayout2.boxStrokeColor = Color.RED
        textInputLayout2.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout2.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout2.hint ="N°Rep"
        var editText2 = TextInputEditText(textInputLayout2.context)
        editText2.inputType = (InputType.TYPE_CLASS_NUMBER  )
        editText2.filters += InputFilter.LengthFilter(3)

        textInputLayout2.setPadding(25,0,25,0)
        textInputLayout2.addView(editText2, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var textInputLayout3 = TextInputLayout(this)
        textInputLayout3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1F)
        textInputLayout3.boxStrokeColor = Color.RED
        textInputLayout3.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout3.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_OUTLINE
        textInputLayout3.hint ="Recupero"
        var editText3 = TextInputEditText(textInputLayout3.context)
        editText3.inputType = (InputType.TYPE_CLASS_NUMBER )
        editText3.filters += InputFilter.LengthFilter(4)

        textInputLayout3.setPadding(25,0,25,0)
        textInputLayout3.addView(editText3, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

        var textInputLayout4 = TextInputLayout(this)
        textInputLayout4.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1F)
        textInputLayout4.boxStrokeColor = Color.RED
        textInputLayout4.boxBackgroundColor = Color.TRANSPARENT
        textInputLayout4.boxBackgroundMode =  TextInputLayout.BOX_BACKGROUND_FILLED
        textInputLayout4.hint ="Nome esercizio"
        var editText4 = TextInputEditText(textInputLayout4.context)

        editText4.filters += InputFilter.LengthFilter(24)

        textInputLayout4.setPadding(25,50,25,25)
        textInputLayout4.addView(editText4, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))



        firstLinearLayout.addView(textView2)
        firstLinearLayout.addView(textInputLayout4)
        linearLayout.addView(textInputLayout)
        linearLayout.addView(textInputLayout2)
        linearLayout.addView(textInputLayout3)


    }
}
