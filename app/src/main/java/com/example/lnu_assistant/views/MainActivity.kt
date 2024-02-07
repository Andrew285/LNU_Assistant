package com.example.lnu_assistant.views

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lnu_assistant.R
import com.example.lnu_assistant.viewmodel.AppViewModel
import com.example.lnu_assistant.views.interfaces.DrawerLocker
import com.example.lnu_assistant.views.interfaces.IFragmentNavigator
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(), DrawerLocker {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: AppViewModel
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)

        firebaseAuth = Firebase.auth

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            navController.navigate(R.id.action_home_dest_to_signInFragment, null)
        }

        findViewById<NavigationView>(R.id.nav_view).setItemIconTintList(null);

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.home_dest),
                drawerLayout)
        // TODO END STEP 9.5

        setupActionBar(navController, appBarConfiguration)

        setupNavigationMenu(navController)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        findViewById<NavigationView>(R.id.nav_view).menu.findItem(R.id.signInFragment)
            .setOnMenuItemClickListener {
                viewModel.singOut()
                navController.navigate(R.id.action_home_dest_to_signInFragment, null)
                findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawers()
                true
            }

        host.childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            supportActionBar?.title = (fragment as IFragmentNavigator).title()
        }
    }

    //Extension function to find current fragment
    private val FragmentManager.currentNavigationFragment: Fragment?
        get() = findFragmentById(R.id.my_nav_host_fragment)?.childFragmentManager?.fragments?.first()


    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode =
            if (enabled) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        drawerLayout?.setDrawerLockMode(lockMode)

        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
        supportActionBar?.setHomeButtonEnabled(enabled)
        supportActionBar?.setDisplayShowHomeEnabled(enabled)
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
}
