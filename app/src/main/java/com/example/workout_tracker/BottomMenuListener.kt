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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class BottomListener : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.statistics -> Log.d(null,"sta")
            R.id.new_workout -> Log.d(null,"new workout")
            R.id.start_workout->  Log.d(null,"startwork")
            R.id.profile_exercise ->  Log.d(null,"pro exe")
            R.id.macro_chart ->  Log.d(null,"macrochatt")
            R.id.add_food->  Log.d(null,"addfood")
            R.id.profile_food ->  Log.d(null,"profilefood")



        }
        return true
    }

}