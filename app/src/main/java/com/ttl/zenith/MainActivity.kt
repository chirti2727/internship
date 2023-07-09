package com.ttl.zenith

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.ttl.zenith.databinding.ActivityLoginBinding
import com.ttl.zenith.databinding.ActivityMainBinding
import com.ttl.zenith.fragment.SettingsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var drawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Set up the navigation drawer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(drawerToggle!!)
        drawerToggle!!.syncState()


        binding. navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    // Navigate to the Home fragment
                    navController.navigate(R.id.homeFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.myVideoFragment -> {
                    // Navigate to the Search fragment
                    navController.navigate(R.id.myVideoFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.videosFragment -> {
                    // Navigate to the Settings fragment
                    navController.navigate(R.id.myVideoFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.recommendation -> {
                    // Navigate to the Settings fragment
                    navController.navigate(R.id.recommendationFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.settingsFragment -> {
                    // Navigate to the Settings fragment
                    navController.navigate(R.id.settingsFragment)

                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.profileFragment -> {
                    // Navigate to the Settings fragment
                    navController.navigate(R.id.profileFragment)
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.logout -> {
                    binding.drawerLayout.closeDrawers()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()

                    true
                }
                else -> false
            }
        }



    }
    fun showBottomNavigation(show: Boolean) {
        val bottomNavigationView =  binding.bottomNavView
        if (show) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
    }
    fun showToolBar(show: Boolean) {
        val drawerLayout =  binding.toolbar
        if (show) {
            drawerLayout.visibility = View.VISIBLE
        } else {
            drawerLayout.visibility = View.GONE
        }
    }
}