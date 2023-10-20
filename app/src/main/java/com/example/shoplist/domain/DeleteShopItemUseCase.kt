package com.example.shoplist.domain

class DeleteShopItemUseCase(private val repository: Repository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        repository.deleteShopItem(shopItem)
    }
}