package com.example.workout_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


/*class DrawerListener : NavigationView.OnNavigationItemSelectedListener {

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
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }
    private fun changeMenu(menu : Int,defoultItem:Int){
        bottom_nav.inflateMenu(menu)
        bottom_nav.visibility=View.VISIBLE
        drawer_layout.closeDrawer((GravityCompat.START))
        bottom_nav.selectedItemId = defoultItem
    }
}*/