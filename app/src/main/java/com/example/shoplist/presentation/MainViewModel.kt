package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.RepositoryImpl
import com.example.shoplist.domain.DeleteShopItemUseCase
import com.example.shoplist.domain.GetShopListUseCase
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.EditShopItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repositoryImpl = RepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repositoryImpl)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repositoryImpl)
    private val editShopItemUseCase = EditShopItemUseCase(repositoryImpl)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val item = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.updateShopItem(item)
        }
    }
}