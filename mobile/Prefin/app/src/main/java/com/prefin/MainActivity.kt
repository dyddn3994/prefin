package com.prefin

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.prefin.config.BaseActivity
import com.prefin.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 액티비티를 세로모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setupNavHost()
    }

    private fun setupNavHost() {
        // NavHostFragment를 가져와서 설정합니다.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 목적지에 따라 nav 바 숨기거나 보이기
            when (destination.id) {

            }
        }

    }
}