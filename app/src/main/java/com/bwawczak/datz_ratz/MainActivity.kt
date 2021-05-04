package com.bwawczak.datz_ratz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var homeFragment: HomeFragment
    lateinit var addSnakeFragment: AddSnakeFragment

    //lateinit var viewPreviousFragment: ViewPreviousFragment
    private lateinit var viewOrderFragment: ViewOrderFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get information sent from registration screen
       // val userId = intent.getStringExtra("user_id")
        //val emailId = intent.getStringExtra("email_id")
//        val firstNameId = intent.getStringExtra("first_name")
//        val lastNameId = intent.getStringExtra("last_name")



        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar?.title = "Datz Ratz"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout,
            toolBar, (R.string.open), (R.string.close)
        ) {

        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //sets homeFragment to default
        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {
            R.id.home -> {
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, homeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.add_snake -> {
                addSnakeFragment = AddSnakeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, addSnakeFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }

            R.id.logout_app -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }

            R.id.view_order -> {
                viewOrderFragment = ViewOrderFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, viewOrderFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START))
        } else {
            super.onBackPressed()
        }
        super.onBackPressed()
    }

    //get rid of keyboard on touch
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}