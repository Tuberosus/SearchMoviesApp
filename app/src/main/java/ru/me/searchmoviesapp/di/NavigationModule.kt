package ru.me.searchmoviesapp.di

import org.koin.dsl.module
import ru.me.searchmoviesapp.core.navigation.Router
import ru.me.searchmoviesapp.core.navigation.RouterImpl

val navigationModule = module {
    val router = RouterImpl()

    single<Router> { router }
    single { router.navigatorHolder }
}