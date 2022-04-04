package ru.babaets.moviedbobserver

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.babaets.moviedbobserver.common.appModule
import ru.babaets.moviedbobserver.network.networkModule
import ru.babaets.moviedbobserver.presentation.feature.feed.feedModule
import ru.babaets.moviedbobserver.presentation.feature.home.homeModule
import ru.babaets.moviedbobserver.presentation.feature.moviecard.movieCardModule

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApp)
            modules(
                appModule,
                networkModule,
                homeModule,
                feedModule,
                movieCardModule
            )
        }
    }
}
