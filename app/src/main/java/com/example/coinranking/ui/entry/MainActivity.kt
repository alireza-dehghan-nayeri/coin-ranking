package com.example.coinranking.ui.entry

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.coinranking.R
import com.example.coinranking.core.hide
import com.example.coinranking.core.hideDrawer
import com.example.coinranking.core.show
import com.example.coinranking.core.showDrawer
import com.example.coinranking.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var btnSwitchTheme: SwitchMaterial
    private lateinit var navController: NavController

    private val topLevelMenuItems = setOf(
        R.id.coinsFragment,
        R.id.bookmarksFragment,
        R.id.exchangesFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initUiComponents()
        initNavController()
        setupToolbar()
        setupBottomNavigationMenu()
        setupNavigationMenu()
        setupNavigationUiState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolBar)
        appBarConfiguration = AppBarConfiguration(topLevelMenuItems, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavigationMenu() {
        binding.drawerNavigationView.setupWithNavController(navController)
    }

    private fun setupBottomNavigationMenu() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupNavigationUiState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.coinsFragment -> {
                    // Show UI controllers
                    binding.bottomNavigation.show()
                    binding.toolBar.show()
                    binding.drawerLayout.showDrawer()
                }

                R.id.splashFragment -> {
                    binding.toolBar.hide()
                    binding.bottomNavigation.hide()
                    binding.drawerLayout.hideDrawer()
                }

                R.id.bookmarksFragment -> {
                    binding.bottomNavigation.show()
                    binding.toolBar.show()
                    binding.drawerLayout.showDrawer()
                }

                R.id.exchangesFragment -> {
                    binding.bottomNavigation.show()
                    binding.toolBar.show()
                    binding.drawerLayout.showDrawer()
                }

                else -> { // Hide UI Controllers
                    binding.toolBar.show()
                    binding.bottomNavigation.hide()
                    binding.drawerLayout.hideDrawer()

                }
            }
        }
    }

    private fun initUiComponents() {
        btnSwitchTheme =
            binding.drawerNavigationView.menu.findItem(R.id.item_night_mode).actionView as SwitchMaterial

        btnSwitchTheme.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}