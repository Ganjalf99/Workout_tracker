package com.example.workout_tracker

import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.workout_tracker.fragment.SettingsFragment
import com.example.workout_tracker.fragment.exercise.*
import com.example.workout_tracker.fragment.food.AddFoodFragment
import com.example.workout_tracker.fragment.food.MacroChartFragment
import com.example.workout_tracker.util.Workout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_statistics.*


class MainActivity : AppCompatActivity() {
    var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser = mAuth.currentUser
    val idUser = currentUser?.uid
    var mUserReference = FirebaseDatabase.getInstance().getReference("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
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
        nav_view.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                bottom_nav.menu.clear()
                when (item.itemId) {
                    R.id.nav_exercise -> {
                        changeMenu(R.menu.bottom_drawer_menu_exercise, R.id.start_workout)
                    }//seleziona l'item di default
                    R.id.nav_food -> {
                        changeMenu(R.menu.bottom_drawer_menu_food, R.id.add_food)
                    }
                    R.id.nav_settings -> {
                        bottom_nav.visibility = View.GONE//nascone il bottom menu
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<SettingsFragment>(R.id.main_fragment)
                        }
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                    R.id.nav_profile -> {
                        bottom_nav.visibility = View.GONE//nascone il bottom menu
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            replace<ExerciseProfileFragment>(R.id.main_fragment)
                        }
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                }
                return true
            }

        })
        //listener per i bottom menu
        bottom_nav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                //cambio il fragment inbase alla icona clicata
                when (item.itemId) {
                    R.id.statistics -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<StatisticsFragment>(R.id.main_fragment)

                    }
                    R.id.new_workout -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<NewTrainingFragment>(R.id.main_fragment)

                    }
                    R.id.start_workout -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<StartWorkoutFragment>(R.id.main_fragment)

                    }

                    R.id.macro_chart -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MacroChartFragment>(R.id.main_fragment)

                    }
                    R.id.add_food -> supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<AddFoodFragment>(R.id.main_fragment)

                    }


                }
                return true
            }

        })


    }

    private fun changeMenu(menu: Int, defoultItem: Int){
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

    public fun addWorkoutToFirebase(workout: Workout){
        mUserReference.child(idUser!!).child(workout.nome).setValue(workout)
        Toast.makeText(this, getString(R.string.allenamento_aggiunto), Toast.LENGTH_SHORT).show()
    }

    fun showWorkoutStatistics(workout: Workout){
        bottom_nav.selectedItemId = R.id.statistics

        object : CountDownTimer(200, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val m =supportFragmentManager.findFragmentById(R.id.main_fragment) as StatisticsFragment
                m.showWorkoutStatistics(workout)
            }
        }.start()

    }

}
