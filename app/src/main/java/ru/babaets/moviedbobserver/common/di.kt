package ru.babaets.moviedbobserver.common

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.babaets.moviedbobserver.common.navigation.AppNavigator

val appModule = module {

    single {
        AppNavigator()
    }

    single<StringProvider> {
        StringProviderImpl(
            resources = androidContext().resources
        )
    }
}
