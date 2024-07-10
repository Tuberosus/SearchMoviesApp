package ru.me.searchmoviesapp.util

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.me.searchmoviesapp.di.dataModule
import ru.me.searchmoviesapp.di.interactorModule
import ru.me.searchmoviesapp.di.repositoryModule
import ru.me.searchmoviesapp.di.viewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}