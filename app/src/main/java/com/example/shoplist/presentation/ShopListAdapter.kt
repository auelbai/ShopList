package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((item: ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((item: ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ENABLE_ITEM -> R.layout.item_shop_enabled
            DISABLE_ITEM -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvText.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            ENABLE_ITEM
        } else DISABLE_ITEM
    }
    companion object {
        const val ENABLE_ITEM = 111
        const val DISABLE_ITEM = 222
        const val RV_POOL_SIZE = 10
    }
}