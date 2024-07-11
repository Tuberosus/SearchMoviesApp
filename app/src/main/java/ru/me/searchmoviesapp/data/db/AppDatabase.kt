package ru.me.searchmoviesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.me.searchmoviesapp.data.db.dao.MovieDao
import ru.me.searchmoviesapp.data.db.entity.MovieEntity

@Database(version = 1, entities = [MovieEntity::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao(): MovieDao
}