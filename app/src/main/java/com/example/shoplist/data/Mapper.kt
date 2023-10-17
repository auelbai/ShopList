package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class Mapper {
    fun mapEntityToModel(shopItem: ShopItem): DbModel {
        return DbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapModelToEntity(model: DbModel): ShopItem {
        return ShopItem(
            name = model.name,
            count = model.count,
            enabled = model.enabled,
            id = model.id
        )
    }

    fun mapModelListToEntityList(list: List<DbModel>): List<ShopItem> {
        return list.map { mapModelToEntity(it) }
    }
}