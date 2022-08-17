package com.lexwilliam.hanki

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.lexwilliam.hanki.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        lifecycleScope.launch {
            val isUserAuthenticated = viewModel.isAuthenticated.value
            if (!isUserAuthenticated) {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://lexwilliam.hanki.app/login_fragment".toUri())
                    .build()
                navController.navigate(request)
            }
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