package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_list")
    fun getShopList(): LiveData<List<DbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShopItem(model: DbModel)

    @Delete
    suspend fun deleteShopItem(model: DbModel)

    @Query("SELECT * FROM shop_list WHERE id=:id LIMIT 1")
    suspend fun getShopItem(id: Int): DbModel
}