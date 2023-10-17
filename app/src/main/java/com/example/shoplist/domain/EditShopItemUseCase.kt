package com.example.shoplist.domain

class EditShopItemUseCase(private val repository: Repository) {

    fun updateShopItem(shopItem: ShopItem) {
        repository.updateShopItem(shopItem)
    }
}