package ru.me.searchmoviesapp.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.me.searchmoviesapp.data.NetworkClient
import ru.me.searchmoviesapp.data.SharedPreferences.LocalStorage
import ru.me.searchmoviesapp.data.converters.MovieCastConverter
import ru.me.searchmoviesapp.data.db.AppDatabase
import ru.me.searchmoviesapp.data.network.IMDbApiService
import ru.me.searchmoviesapp.data.network.RetrofitNetworkClient

val dataModule = module {

    single<IMDbApiService> {
        Retrofit.Builder()
        .baseUrl("https://tv-api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IMDbApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single {
        androidContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    single {
        LocalStorage(get())
    }

    single {
        MovieCastConverter()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }

}