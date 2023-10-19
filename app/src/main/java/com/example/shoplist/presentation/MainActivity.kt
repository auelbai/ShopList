package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.main_shop_item_container)
        setUpRV()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        if (isOnePainMode()) {
            addBtn.setOnClickListener {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
        } else {
            launchFragment(ShopItemFragment.newInstanceAddMode())
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun setUpRV() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        with(rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLE_ITEM, ShopListAdapter.RV_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLE_ITEM, ShopListAdapter.RV_POOL_SIZE
            )
        }
        itemClickListeners()
        swipeToDelete(rvShopList)
    }

    private fun itemClickListeners() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }

        shopListAdapter.onShopItemClickListener = {
            if (isOnePainMode()) {
                ShopItemActivity.newIntentEditItem(this, it.id)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditMode(it.id))
            }
        }
    }

    private fun isOnePainMode() = shopItemContainer == null

    private fun swipeToDelete(rvShopList: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        })
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    override fun onEditingFinish() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }
}