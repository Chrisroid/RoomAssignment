package com.example.zuri.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zuri.room.databinding.ActivityContactsBinding
import com.example.zuri.room.databinding.ContactItemBinding
import com.example.zuri.room.db.Contact
import com.example.zuri.room.db.ContactCategory
import com.example.zuri.room.db.UserEntity
import com.example.zuri.room.db.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var user: UserEntity
    private lateinit var category: ContactCategory
    private lateinit var adapter: Adapter<Contact>
    private lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDisplay()
    }

    private fun setupDisplay() {
        title = intent.getStringExtra(NAME)
        val mBinding = ActivityContactsBinding.inflate(layoutInflater)
        val dialog = Dialog(NAME, this)

        repository = UserRepository(this)
        email = intent.getStringExtra(UserEntity.USER).toString()
        adapter = Adapter<Contact>({ parent ->
            ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        },{binding, contact ->
            (binding as ContactItemBinding).let {
                it.tvName.text = contact.name
                it.tvNumber.text = contact.number
            }
        })

        mBinding.let{
            setContentView(it.root)
            it.rvContacts.layoutManager = LinearLayoutManager(this)
            it.rvContacts.adapter = adapter
            it.fabContacts.setOnClickListener{ dialog.dialog.show() }
        }
        
        dialog.dialogBinding.let{it ->
            it.btnAdd.setOnClickListener { _ ->
                if(it.tilName.editText?.text.toString().isNotBlank()){
                    val contact = Contact(it.tilName.editText?.text.toString(), it.tilPhone.editText?.text.toString())
                    adapter.addItem(contact)
                    category.contacts.add(contact)
                    dialog.dialog.dismiss()
                } else
                    Toast.makeText(this, "$NAME name should not be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        repository.updateUser(user)
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            user = repository.findUserByEmail(email)!!
            user.contactCategories.let{
                for (contactCategory in it)
                    if(contactCategory.name.equals(title as String?, true))
                        this@ContactsActivity.category = contactCategory
                withContext(Dispatchers.Main){
                    adapter.removeThenAddItems(category.contacts)
                }
            }
        }
    }

    companion object{
        const val NAME = "Contact"
        const val CONTENT = "Contacts"
    }
}