package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_list")
    fun getShopList(): LiveData<List<DbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(model: DbModel)

    @Delete
    fun deleteShopItem(model: DbModel)

    @Query("SELECT * FROM shop_list WHERE id=:id LIMIT 1")
    fun getShopItem(id: Int): DbModel
}