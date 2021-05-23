package com.example.zuri.room

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zuri.room.databinding.ActivityLoginBinding
import com.example.zuri.room.db.UserEntity
import com.example.zuri.room.db.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()
    }

    private fun setup() {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        val repository = UserRepository(this)
        setContentView(binding.root)

        val displayToast =  { text: String ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        binding.btnNext.setOnClickListener {
            val email = binding.tilEmail.editText?.text
            val password = binding.tilPassword.editText?.text
            if(email?.isBlank() == false && password?.isBlank() == false){
                GlobalScope.launch {
                    val user = repository.findUserByEmail(email.toString())
                    withContext(Dispatchers.Main){
                        user ?: displayToast ("No account is registered with this email, please create an account in the signup page")
                        user?.let {
                            if(user.email.equals(email.toString(), ignoreCase = true) && user.password.equals(password.toString())){
                                val intent = Intent(this@LoginActivity, ContactCategoryActivity::class.java)
                                intent.putExtra(UserEntity.USER, user.email)
                                startActivity(intent)
                            }else displayToast ("Incorrect password")
                        }
                    }
                }
            }else displayToast("One or more fields are blank")
        }

        val signUp_txt = findViewById<TextView>(R.id.signUp_txt)

        signUp_txt.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}