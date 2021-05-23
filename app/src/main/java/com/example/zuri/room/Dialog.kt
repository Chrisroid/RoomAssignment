package com.example.zuri.room

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.zuri.room.databinding.NewItemDialogBinding

class Dialog(val header: String, private val activity: AppCompatActivity) {
    val dialogBinding = NewItemDialogBinding.inflate(activity.layoutInflater)
    lateinit var dialog: AlertDialog

    init {
        setupDialog()
    }

    private fun setupDialog(){
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBinding.root)
        dialog = builder.create()

        dialogBinding.let{it ->
            it.tvDialogHeader.text =  "${it.tvDialogHeader.text} ${header}"
            it.tilPhone.editText?.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    it.btnAdd.isEnabled = p0?.length == 11
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

}