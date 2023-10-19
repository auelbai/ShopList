package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.content.Intent.parseIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishListener {

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()
        launchRightScreenMode()
    }

    private fun launchRightScreenMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddMode()
            MODE_EDIT -> ShopItemFragment.newInstanceEditMode(shopItemId)
            else -> throw RuntimeException("unknown mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_MODE)) {
            throw RuntimeException("intent has not extra")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("unknown mode $mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ID)) {
                throw RuntimeException("mode edit has not id")
            }
            shopItemId = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val EXTRA_ID = "extra_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = "mode_unknown"

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

    override fun onEditingFinish() {
        finish()
    }
}