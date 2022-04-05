package ru.babaets.moviedbobserver.common

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.babaets.moviedbobserver.common.navigation.AppNavigator
import ru.babaets.moviedbobserver.common.navigation.AppNavigatorImpl

val appModule = module {

    single<AppNavigator> {
        AppNavigatorImpl()
    }

    single<StringProvider> {
        StringProviderImpl(
            resources = androidContext().resources
        )
    }
}
