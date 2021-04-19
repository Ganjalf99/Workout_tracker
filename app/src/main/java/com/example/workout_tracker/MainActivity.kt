package com.example.workout_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

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
        bottom_nav.selectedItemId = R.id.start_workout
        supportFragmentManager.commit { setReorderingAllowed(true)
            replace<StartWorkoutFragment>(R.id.main_fragment)
        }
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
        bottom_nav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
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


}
