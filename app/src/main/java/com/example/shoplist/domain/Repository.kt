package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun getShopList(): LiveData<List<ShopItem>>

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun updateShopItem(shopItem: ShopItem)

    fun getShopItem(id: Int): ShopItem
}