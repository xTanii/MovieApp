package com.example.tanyanarsinghani.moviebuzz.Repository

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.tanyanarsinghani.moviebuzz.Model.Moviedata.MovieData

@Database(entities = ( arrayOf(MovieData::class)), version = 1)
@TypeConverters
abstract class MovieDatabase: RoomDatabase() {
    abstract fun MovieDao(): MovieDao
    companion object {
        private var INSTANCE: RoomDatabase? = null
        fun getDatabase(context: Context): RoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        RoomDatabase::class.java, "chapter.db"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                        .build()
                }
            }
            return INSTANCE
        }
    }

}

