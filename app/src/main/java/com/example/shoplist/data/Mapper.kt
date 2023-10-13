package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class Mapper {
    fun mapEntityToModel(shopItem: ShopItem): DbModel {
        return DbModel(
            shopItem.id,
            shopItem.name,
            shopItem.count,
            shopItem.enabled
        )
    }

    fun mapModelToEntity(shopItem: DbModel): ShopItem {
        return ShopItem(
            shopItem.id,
            shopItem.name,
            shopItem.count,
            shopItem.enabled
        )
    }

    fun mapModelListToEntityList(list: List<DbModel>): List<ShopItem> {
        return list.map { mapModelToEntity(it) }
    }
}