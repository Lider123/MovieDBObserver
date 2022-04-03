package ru.babaets.moviedbobserver.common

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<StringProvider> {
        StringProviderImpl(
            resources = androidContext().resources
        )
    }
}
