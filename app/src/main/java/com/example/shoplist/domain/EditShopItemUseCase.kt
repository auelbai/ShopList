package com.example.shoplist.domain

class EditShopItemUseCase(private val repository: Repository) {

    suspend fun updateShopItem(shopItem: ShopItem) {
        repository.updateShopItem(shopItem)
    }
}