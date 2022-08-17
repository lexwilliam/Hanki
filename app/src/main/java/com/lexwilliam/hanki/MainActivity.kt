package com.lexwilliam.hanki

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lexwilliam.hanki.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val user = auth.currentUser
        if (user == null) {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://lexwilliam.hanki.app/login_fragment".toUri())
                .build()
            navController.navigate(request)
        }

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, bundle ->
            if(destination.id == com.lexwilliam.feature_home.R.id.homeFragment
                || destination.id == com.lexwilliam.feature_explore.R.id.exploreFragment
                || destination.id == com.lexwilliam.feature_packs.R.id.packsFragment) {
                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNavView.apply {
            setupWithNavController(navController)
        }
    }
}