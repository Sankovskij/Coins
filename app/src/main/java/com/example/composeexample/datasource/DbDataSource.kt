package com.example.composeexample.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composeexample.model.Coin
import com.example.composeexample.model.CoinDao

@Database(entities = [Coin::class], version = 1)
abstract class DbDataSource: RoomDatabase() {
    abstract fun coinDao(): CoinDao
}