package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopItemViewModel: ShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]


    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val EXTRA_ID = "extra_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ID, id)
            return intent
        }
    }
}