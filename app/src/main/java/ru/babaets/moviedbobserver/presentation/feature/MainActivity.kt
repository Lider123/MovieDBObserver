package ru.babaets.moviedbobserver.presentation.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import org.koin.android.ext.android.inject
import ru.babaets.moviedbobserver.R
import ru.babaets.moviedbobserver.common.navigation.AppNavigator

class MainActivity : AppCompatActivity() {

    private val navigator: AppNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.controller = findNavController(R.id.fragmentNavHost)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fragmentNavHost).navigateUp()
}
