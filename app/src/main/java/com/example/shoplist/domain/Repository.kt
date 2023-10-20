package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getShopList(): LiveData<List<ShopItem>>

    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun updateShopItem(shopItem: ShopItem)

    suspend fun getShopItem(id: Int): ShopItem
}