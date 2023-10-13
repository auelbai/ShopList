package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoplist.data.RepositoryImpl
import com.example.shoplist.domain.DeleteShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.UpdateShopItemUseCase

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repositoryImpl = RepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repositoryImpl)
    private val updateShopItemUseCase = UpdateShopItemUseCase(repositoryImpl)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val item = shopItem.copy(enabled = !shopItem.enabled)
        updateShopItemUseCase.updateShopItem(item)
    }
}