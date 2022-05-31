package com.example.composeexample.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "favourite")
data class Coin(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String

)

@Dao
interface CoinDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: Coin)

    @Query("SELECT * FROM favourite ORDER BY id DESC")
    fun getAll(): LiveData<List<Coin>>

    @Query("SELECT * FROM favourite WHERE name = :name")
    fun getCoin(name: String): Coin?

    @Query("DELETE FROM favourite WHERE name = :name")
    fun delete(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoin(coin: Coin)
}
