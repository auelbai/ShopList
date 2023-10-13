package com.example.shoplist.domain

class UpdateShopItemUseCase(private val repository: Repository) {

    fun updateShopItem(shopItem: ShopItem) {
        repository.updateShopItem(shopItem)
    }
}