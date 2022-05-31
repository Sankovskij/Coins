package com.example.composeexample.repository

import androidx.lifecycle.LiveData
import com.example.composeexample.datasource.RestDataSource
import com.example.composeexample.model.Coin
import com.example.composeexample.model.Response
import com.example.composeexample.model.CoinDao
import retrofit2.Call
import javax.inject.Inject

interface CoinRepository {
    suspend fun getLatest(coin: String): Response
    suspend fun deleteCoin(name: String)
    suspend fun getAllCoins(): LiveData<List<Coin>>
    fun getCoin(name: String): Coin?
    suspend fun insertCoin(coin: Coin)
}

class CoinRepositoryImpl @Inject constructor(
    private val dataSource: RestDataSource,
    private val coinDao: CoinDao
) : CoinRepository {
    override suspend fun getLatest(coin: String) = dataSource.getLatest(coin)

    override suspend fun deleteCoin(name: String) = coinDao.delete(name)

    override suspend fun getAllCoins(): LiveData<List<Coin>> = coinDao.getAll()

    override fun getCoin(name: String): Coin? = coinDao.getCoin(name)

    override suspend fun insertCoin(coin: Coin) = coinDao.insertCoin(coin)
}