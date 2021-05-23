package com.example.zuri.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zuri.room.databinding.*
import com.example.zuri.room.databinding.ActivityContactCategoryBinding
import com.example.zuri.room.databinding.ContactCategoryItemBinding
import com.example.zuri.room.db.ContactCategory
import com.example.zuri.room.db.UserEntity
import com.example.zuri.room.db.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactCategoryActivity : AppCompatActivity() {
    private lateinit var user: UserEntity
    private lateinit var adapter: Adapter<ContactCategory>
    private lateinit var repository: UserRepository
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDisplay()
    }

    private fun setupDisplay() {
        val mBinding = ActivityContactCategoryBinding.inflate(layoutInflater)
        val dialog = Dialog(NAME, this)

        repository = UserRepository(this)
        email = intent.getStringExtra(UserEntity.USER).toString()
        adapter = Adapter<ContactCategory>({ parent ->
            val itemBinding = ContactCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            itemBinding.root.setOnClickListener { _ ->
                val intent = Intent(this, ContactsActivity::class.java)
                intent.putExtra(ContactsActivity.NAME, itemBinding.tvCategoryName.text.toString())
                intent.putExtra(UserEntity.USER, email)
                startActivity(intent)
            }
            return@Adapter itemBinding
        },{ binding, contactCategory ->
            (binding as ContactCategoryItemBinding).tvCategoryName.text = contactCategory.name
        })

        dialog.dialogBinding.let{it ->
            it.tilPhone.visibility = View.INVISIBLE
            it.btnAdd.isEnabled = true
            it.btnAdd.setOnClickListener { _ ->
                if(it.tilName.editText?.text.toString().isNotBlank()) {
                    val contactCategory = ContactCategory(it.tilName.editText?.text.toString())
                    adapter.addItem(contactCategory)
                    dialog.dialog.dismiss()
                    user.contactCategories.add(contactCategory)
                }else
                    Toast.makeText(this, "$NAME name should not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.let{
            setContentView(it.root)
            it.rvCategory.layoutManager = GridLayoutManager(this, 2)
            it.rvCategory.adapter = adapter
            it.fabCategory.setOnClickListener{ dialog.dialog.show() }
        }
    }

    companion object{
        const val NAME = "Category"
    }

    override fun onPause() {
        super.onPause()
        repository.updateUser(user)
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            user = repository.findUserByEmail(email)!!
            withContext(Dispatchers.Main){
                adapter.removeThenAddItems(user.contactCategories)
            }
        }
    }
}