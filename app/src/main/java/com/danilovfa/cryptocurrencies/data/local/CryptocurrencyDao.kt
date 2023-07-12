package com.danilovfa.cryptocurrencies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyChartEntity
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyDetailsEntity
import com.danilovfa.cryptocurrencies.data.local.model.CryptocurrencyItemEntity
import com.danilovfa.cryptocurrencies.data.local.model.UserEntity

@Dao
interface CryptocurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setChart(chart: CryptocurrencyChartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setItem(item: CryptocurrencyItemEntity)

    @Transaction
    @Query("SELECT * FROM items WHERE item_id = :id")
    suspend fun getDetails(id: Int): CryptocurrencyDetailsEntity

    @Query("SELECT * FROM items")
    suspend fun getItems(): List<CryptocurrencyItemEntity>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)
}