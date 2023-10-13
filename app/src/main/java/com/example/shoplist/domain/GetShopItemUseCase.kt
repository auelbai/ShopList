package com.example.shoplist.domain

class GetShopItemUseCase(private val repository: Repository) {

    fun getShopItem(id: Int): ShopItem {
        return repository.getShopItem(id)
    }
}