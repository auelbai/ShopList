package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

class RepositoryImpl(application: Application) : Repository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = Mapper()

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListDao.getShopList().map {
            mapper.mapModelListToEntityList(it)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(mapper.mapEntityToModel(shopItem))
    }

    override fun updateShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToModel(shopItem))
    }

    override fun getShopItem(id: Int): ShopItem {
        return mapper.mapModelToEntity(shopListDao.getShopItem(id))
    }
}