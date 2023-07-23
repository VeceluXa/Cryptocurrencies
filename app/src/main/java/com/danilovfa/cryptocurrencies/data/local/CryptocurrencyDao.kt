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
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PER_PAGE_DEFAULT
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptocurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setChart(chart: CryptocurrencyChartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setItems(items: List<CryptocurrencyItemEntity>)

    @Query("SELECT * FROM items LIMIT :limit OFFSET :offset ")
    fun getItemsByPage(offset: Int, limit: Int = PER_PAGE_DEFAULT): List<CryptocurrencyItemEntity>

    @Transaction
    @Query("SELECT * FROM items WHERE coin_id = :id")
    suspend fun getDetails(id: String): CryptocurrencyDetailsEntity

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("DELETE FROM items")
    suspend fun clearCache()
}