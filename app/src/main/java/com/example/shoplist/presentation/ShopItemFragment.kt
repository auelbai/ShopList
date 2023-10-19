package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.ShopItemActivity.Companion.MODE_ADD
import com.example.shoplist.presentation.ShopItemActivity.Companion.MODE_EDIT
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment(): Fragment() {

    private lateinit var viewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

    private lateinit var screenMode: String
    private var shopItemId: Int = ShopItem.UNDEFINED_ID

    lateinit var onEditingFinishListener: OnEditingFinishListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        launchRightMode()
        observeViewModels()
        resetErrors()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
            etName.setText(it.name)
            etCount.setText(it.count)
        }
        viewModel.editShopItemUseCase(etName.text?.toString(), etCount.text?.toString())
    }

    private fun resetErrors() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorName()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorCount()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun observeViewModels() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                tilName.error = "not correct name"
            } else tilName.error = null
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                tilCount.error = "not correct count"
            } else tilCount.error = null
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_button)
    }

    private fun parseParams() {
        if (!requireArguments().containsKey(EXTRA_MODE)) {
            throw RuntimeException("intent has not extra")
        }
        val mode = requireArguments().getString(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("unknown mode $mode")
        }
        screenMode = mode
        if (mode == MODE_EDIT) {
            if (!requireArguments().containsKey(EXTRA_ID)) {
                throw RuntimeException("mode edit has not id")
            }
            shopItemId = requireArguments().getInt(EXTRA_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishListener {
        fun onEditingFinish()
    }

    companion object {
        const val EXTRA_MODE = "extra_mode"
        const val EXTRA_ID = "extra_id"
        const val MODE_ADD = "mode_add"
        const val MODE_EDIT = "mode_edit"
        const val MODE_UNKNOWN = "mode_unknown"

        fun newInstanceAddMode(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditMode(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                    putInt(EXTRA_ID, shopItemId)
                }
            }
        }
    }
}